/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.BookRequests;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Countries;
import Model.Readers;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class BookRequestsJpaController implements Serializable
{

    public BookRequestsJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(BookRequests bookRequests)
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Countries countryId = bookRequests.getCountryId();
            if (countryId != null)
            {
                countryId = em.getReference(countryId.getClass(), countryId.getId());
                bookRequests.setCountryId(countryId);
            }
            Readers readerId = bookRequests.getReaderId();
            if (readerId != null)
            {
                readerId = em.getReference(readerId.getClass(), readerId.getId());
                bookRequests.setReaderId(readerId);
            }
            em.persist(bookRequests);
            if (countryId != null)
            {
                countryId.getBookRequestsCollection().add(bookRequests);
                countryId = em.merge(countryId);
            }
            if (readerId != null)
            {
                readerId.getBookRequestsCollection().add(bookRequests);
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

    public void edit(BookRequests bookRequests) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            BookRequests persistentBookRequests = em.find(BookRequests.class, bookRequests.getId());
            Countries countryIdOld = persistentBookRequests.getCountryId();
            Countries countryIdNew = bookRequests.getCountryId();
            Readers readerIdOld = persistentBookRequests.getReaderId();
            Readers readerIdNew = bookRequests.getReaderId();
            if (countryIdNew != null)
            {
                countryIdNew = em.getReference(countryIdNew.getClass(), countryIdNew.getId());
                bookRequests.setCountryId(countryIdNew);
            }
            if (readerIdNew != null)
            {
                readerIdNew = em.getReference(readerIdNew.getClass(), readerIdNew.getId());
                bookRequests.setReaderId(readerIdNew);
            }
            bookRequests = em.merge(bookRequests);
            if (countryIdOld != null && !countryIdOld.equals(countryIdNew))
            {
                countryIdOld.getBookRequestsCollection().remove(bookRequests);
                countryIdOld = em.merge(countryIdOld);
            }
            if (countryIdNew != null && !countryIdNew.equals(countryIdOld))
            {
                countryIdNew.getBookRequestsCollection().add(bookRequests);
                countryIdNew = em.merge(countryIdNew);
            }
            if (readerIdOld != null && !readerIdOld.equals(readerIdNew))
            {
                readerIdOld.getBookRequestsCollection().remove(bookRequests);
                readerIdOld = em.merge(readerIdOld);
            }
            if (readerIdNew != null && !readerIdNew.equals(readerIdOld))
            {
                readerIdNew.getBookRequestsCollection().add(bookRequests);
                readerIdNew = em.merge(readerIdNew);
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Long id = bookRequests.getId();
                if (findBookRequests(id) == null)
                {
                    throw new NonexistentEntityException("The bookRequests with id " + id + " no longer exists.");
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

    public void destroy(Long id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            BookRequests bookRequests;
            try
            {
                bookRequests = em.getReference(BookRequests.class, id);
                bookRequests.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The bookRequests with id " + id + " no longer exists.", enfe);
            }
            Countries countryId = bookRequests.getCountryId();
            if (countryId != null)
            {
                countryId.getBookRequestsCollection().remove(bookRequests);
                countryId = em.merge(countryId);
            }
            Readers readerId = bookRequests.getReaderId();
            if (readerId != null)
            {
                readerId.getBookRequestsCollection().remove(bookRequests);
                readerId = em.merge(readerId);
            }
            em.remove(bookRequests);
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

    public List<BookRequests> findBookRequestsEntities()
    {
        return findBookRequestsEntities(true, -1, -1);
    }

    public List<BookRequests> findBookRequestsEntities(int maxResults, int firstResult)
    {
        return findBookRequestsEntities(false, maxResults, firstResult);
    }

    private List<BookRequests> findBookRequestsEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BookRequests.class));
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

    public BookRequests findBookRequests(Long id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(BookRequests.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getBookRequestsCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BookRequests> rt = cq.from(BookRequests.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally
        {
            em.close();
        }
    }
    
}
