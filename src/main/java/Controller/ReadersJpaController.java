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
import Model.Countries;
import java.util.ArrayList;
import java.util.List;
import Model.Borrows;
import Model.Readers;
import Materials.Factory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 * @author admin
 */
public class ReadersJpaController implements Serializable
{

//    public ReadersJpaController(EntityManagerFactory emf)
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
    
    public static void create(Readers readers)
    {
        if (readers.getBorrowsList() == null)
        {
            readers.setBorrowsList(new ArrayList<Borrows>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Countries countryId = readers.getCountryId();
            if (countryId != null)
            {
                countryId = em.getReference(countryId.getClass(), countryId.getId());
                readers.setCountryId(countryId);
            }
            List<Borrows> attachedBorrowsList = new ArrayList<>();
            for (Borrows borrowsListBorrowsToAttach : readers.getBorrowsList())
            {
                borrowsListBorrowsToAttach = em.getReference(borrowsListBorrowsToAttach.getClass(), borrowsListBorrowsToAttach.getId());
                attachedBorrowsList.add(borrowsListBorrowsToAttach);
            }
            readers.setBorrowsList(attachedBorrowsList);
            em.persist(readers);
            if (countryId != null)
            {
                countryId.getReadersList().add(readers);
                countryId = em.merge(countryId);
            }
            for (Borrows borrowsListBorrows : readers.getBorrowsList())
            {
                Readers oldReaderIdOfBorrowsListBorrows = borrowsListBorrows.getReaderId();
                borrowsListBorrows.setReaderId(readers);
                borrowsListBorrows = em.merge(borrowsListBorrows);
                if (oldReaderIdOfBorrowsListBorrows != null)
                {
                    oldReaderIdOfBorrowsListBorrows.getBorrowsList().remove(borrowsListBorrows);
                    oldReaderIdOfBorrowsListBorrows = em.merge(oldReaderIdOfBorrowsListBorrows);
                }
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

    public static void edit(Readers readers) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Readers persistentReaders = em.find(Readers.class, readers.getId());
            Countries countryIdOld = persistentReaders.getCountryId();
            Countries countryIdNew = readers.getCountryId();
            List<Borrows> borrowsListOld = persistentReaders.getBorrowsList();
            List<Borrows> borrowsListNew = readers.getBorrowsList();
            if (borrowsListNew == null)
            {
                borrowsListNew = new ArrayList<>();
            }
            if (countryIdNew != null)
            {
                countryIdNew = em.getReference(countryIdNew.getClass(), countryIdNew.getId());
                readers.setCountryId(countryIdNew);
            }
            List<Borrows> attachedBorrowsListNew = new ArrayList<>();
            for (Borrows borrowsListNewBorrowsToAttach : borrowsListNew)
            {
                borrowsListNewBorrowsToAttach = em.getReference(borrowsListNewBorrowsToAttach.getClass(), borrowsListNewBorrowsToAttach.getId());
                attachedBorrowsListNew.add(borrowsListNewBorrowsToAttach);
            }
            borrowsListNew = attachedBorrowsListNew;
            readers.setBorrowsList(borrowsListNew);
            readers = em.merge(readers);
            if (countryIdOld != null && !countryIdOld.equals(countryIdNew))
            {
                countryIdOld.getReadersList().remove(readers);
                countryIdOld = em.merge(countryIdOld);
            }
            if (countryIdNew != null && !countryIdNew.equals(countryIdOld))
            {
                countryIdNew.getReadersList().add(readers);
                countryIdNew = em.merge(countryIdNew);
            }
            for (Borrows borrowsListOldBorrows : borrowsListOld)
            {
                if (!borrowsListNew.contains(borrowsListOldBorrows))
                {
                    borrowsListOldBorrows.setReaderId(null);
                    borrowsListOldBorrows = em.merge(borrowsListOldBorrows);
                }
            }
            for (Borrows borrowsListNewBorrows : borrowsListNew)
            {
                if (!borrowsListOld.contains(borrowsListNewBorrows))
                {
                    Readers oldReaderIdOfBorrowsListNewBorrows = borrowsListNewBorrows.getReaderId();
                    borrowsListNewBorrows.setReaderId(readers);
                    borrowsListNewBorrows = em.merge(borrowsListNewBorrows);
                    if (oldReaderIdOfBorrowsListNewBorrows != null && !oldReaderIdOfBorrowsListNewBorrows.equals(readers))
                    {
                        oldReaderIdOfBorrowsListNewBorrows.getBorrowsList().remove(borrowsListNewBorrows);
                        oldReaderIdOfBorrowsListNewBorrows = em.merge(oldReaderIdOfBorrowsListNewBorrows);
                    }
                }
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = readers.getId();
                if (findReaders(id) == null)
                {
                    throw new NonexistentEntityException("The readers with id " + id + " no longer exists.");
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

    public static void destroy(Integer id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Readers readers;
            try
            {
                readers = em.getReference(Readers.class, id);
                readers.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The readers with id " + id + " no longer exists.", enfe);
            }
            Countries countryId = readers.getCountryId();
            if (countryId != null)
            {
                countryId.getReadersList().remove(readers);
                countryId = em.merge(countryId);
            }
            List<Borrows> borrowsList = readers.getBorrowsList();
            for (Borrows borrowsListBorrows : borrowsList)
            {
                borrowsListBorrows.setReaderId(null);
                borrowsListBorrows = em.merge(borrowsListBorrows);
            }
            em.remove(readers);
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

    public static List<Readers> findReadersEntities()
    {
        return findReadersEntities(true, -1, -1);
    }

    public static List<Readers> findReadersEntities(int maxResults, int firstResult)
    {
        return findReadersEntities(false, maxResults, firstResult);
    }

    private static List<Readers> findReadersEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            cq.select(cq.from(Readers.class)).where(em.getCriteriaBuilder().notEqual(cq.from(Readers.class).get("status"), 0));
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Readers> cq = criteriaBuilder.createQuery(Readers.class);
            Root<Readers> root = cq.from(Readers.class);
            cq.select(root).where(criteriaBuilder.isNull(root.get("status")));
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

    public static Readers findReaders(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Readers.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public static int getReadersCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Readers> rt = cq.from(Readers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally
        {
            em.close();
        }
    }
    
    public static Readers findReaders(String memberCard)
    {
        EntityManager em = getEntityManager();
        try
        {
            TypedQuery<Readers> query = em.createNamedQuery("Readers.findByMemberCard", Readers.class);
            query.setParameter("memberCard", memberCard);
            
            Readers obj;
            
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
    
    public static void deleteSafe(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            TypedQuery<Readers> query = em.createNamedQuery("Readers.updateStatus", Readers.class);
            
            query.setParameter("id", id);
            
            query.executeUpdate();
        }
        finally
        {
            em.close();
        }
    }
}
