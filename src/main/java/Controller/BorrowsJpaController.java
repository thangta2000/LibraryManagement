/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Books;
import Model.Borrows;
import Model.Readers;
import Materials.Factory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author admin
 */
public class BorrowsJpaController implements Serializable
{

//    public BorrowsJpaController(EntityManagerFactory emf)
//    {
//        this.emf = emf;
//    }
//
//    private EntityManagerFactory emf = null;
//
//    public EntityManager getEntityManager()
//    {
//        return emf.createEntityManager();
//    }

    public static EntityManager getEntityManager()
    {
        return Factory.getEntityManager();
    }
    
    public static void create(Borrows borrows)
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Books bookId = borrows.getBookId();
            if (bookId != null)
            {
                bookId = em.getReference(bookId.getClass(), bookId.getId());
                borrows.setBookId(bookId);
            }
            Readers readerId = borrows.getReaderId();
            if (readerId != null)
            {
                readerId = em.getReference(readerId.getClass(), readerId.getId());
                borrows.setReaderId(readerId);
            }
            em.persist(borrows);
            if (bookId != null)
            {
                bookId.getBorrowsList().add(borrows);
                bookId = em.merge(bookId);
            }
            if (readerId != null)
            {
                readerId.getBorrowsList().add(borrows);
                readerId = em.merge(readerId);
            }
            em.getTransaction().commit();
        }
        finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public static void edit(Borrows borrows) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Borrows persistentBorrows = em.find(Borrows.class, borrows.getId());
            Books bookIdOld = persistentBorrows.getBookId();
            Books bookIdNew = borrows.getBookId();
            Readers readerIdOld = persistentBorrows.getReaderId();
            Readers readerIdNew = borrows.getReaderId();
            if (bookIdNew != null)
            {
                bookIdNew = em.getReference(bookIdNew.getClass(), bookIdNew.getId());
                borrows.setBookId(bookIdNew);
            }
            if (readerIdNew != null)
            {
                readerIdNew = em.getReference(readerIdNew.getClass(), readerIdNew.getId());
                borrows.setReaderId(readerIdNew);
            }
            borrows = em.merge(borrows);
            if (bookIdOld != null && !bookIdOld.equals(bookIdNew))
            {
                bookIdOld.getBorrowsList().remove(borrows);
                bookIdOld = em.merge(bookIdOld);
            }
            if (bookIdNew != null && !bookIdNew.equals(bookIdOld))
            {
                bookIdNew.getBorrowsList().add(borrows);
                bookIdNew = em.merge(bookIdNew);
            }
            if (readerIdOld != null && !readerIdOld.equals(readerIdNew))
            {
                readerIdOld.getBorrowsList().remove(borrows);
                readerIdOld = em.merge(readerIdOld);
            }
            if (readerIdNew != null && !readerIdNew.equals(readerIdOld))
            {
                readerIdNew.getBorrowsList().add(borrows);
                readerIdNew = em.merge(readerIdNew);
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Long id = borrows.getId();
                if (findBorrows(id) == null)
                {
                    throw new NonexistentEntityException("The borrows with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
        finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public static void destroy(Long id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Borrows borrows;
            try
            {
                borrows = em.getReference(Borrows.class, id);
                borrows.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The borrows with id " + id + " no longer exists.", enfe);
            }
            Books bookId = borrows.getBookId();
            if (bookId != null)
            {
                bookId.getBorrowsList().remove(borrows);
                bookId = em.merge(bookId);
            }
            Readers readerId = borrows.getReaderId();
            if (readerId != null)
            {
                readerId.getBorrowsList().remove(borrows);
                readerId = em.merge(readerId);
            }
            em.remove(borrows);
            em.getTransaction().commit();
        }
        finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public static List<Borrows> findBorrowsEntities()
    {
        return findBorrowsEntities(true, -1, -1);
    }

    public static List<Borrows> findBorrowsEntities(int maxResults, int firstResult)
    {
        return findBorrowsEntities(false, maxResults, firstResult);
    }

    private static List<Borrows> findBorrowsEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Borrows.class));
            Query q = em.createQuery(cq);
            if (!all)
            {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public static Borrows findBorrows(Long id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Borrows.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public static int getBorrowsCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Borrows> rt = cq.from(Borrows.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally
        {
            em.close();
        }
    }
    
    public static Borrows findBorrow(Long bookId)
    {
        EntityManager em = getEntityManager();
        try
        {
            TypedQuery<Borrows> query = em.createNamedQuery("Borrows.findByBookId", Borrows.class);
            query.setParameter("bookId", bookId);
            
            Borrows obj;
            
            var result = query.getSingleResult();
            
            if (result != null)
            {
                obj = result;
            }
            else
            {
                obj = null;
            }
            
            return obj;
        }
        finally
        {
            em.close();
        }
    }
    
    public static List<Borrows> findListBorrow(Integer readerId)
    {
        EntityManager em = getEntityManager();
        try
        {
            TypedQuery<Borrows> query = em.createNamedQuery("Borrows.findByReader", Borrows.class);
            query.setParameter("readerId", readerId);
            
            List<Borrows> list;
            
            var result = query.getResultList();
            
            if (result != null)
            {
                list = result;
            }
            else
            {
                list = null;
            }
            
            return list;
        }
        finally
        {
            em.close();
        }
    }
    
}
