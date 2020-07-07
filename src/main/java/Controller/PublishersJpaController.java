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
import Model.BookTitles;
import Model.Publishers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class PublishersJpaController implements Serializable
{

    public PublishersJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Publishers publishers)
    {
        if (publishers.getBookTitlesCollection() == null)
        {
            publishers.setBookTitlesCollection(new ArrayList<BookTitles>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Countries countryId = publishers.getCountryId();
            if (countryId != null)
            {
                countryId = em.getReference(countryId.getClass(), countryId.getId());
                publishers.setCountryId(countryId);
            }
            Collection<BookTitles> attachedBookTitlesCollection = new ArrayList<BookTitles>();
            for (BookTitles bookTitlesCollectionBookTitlesToAttach : publishers.getBookTitlesCollection())
            {
                bookTitlesCollectionBookTitlesToAttach = em.getReference(bookTitlesCollectionBookTitlesToAttach.getClass(), bookTitlesCollectionBookTitlesToAttach.getId());
                attachedBookTitlesCollection.add(bookTitlesCollectionBookTitlesToAttach);
            }
            publishers.setBookTitlesCollection(attachedBookTitlesCollection);
            em.persist(publishers);
            if (countryId != null)
            {
                countryId.getPublishersCollection().add(publishers);
                countryId = em.merge(countryId);
            }
            for (BookTitles bookTitlesCollectionBookTitles : publishers.getBookTitlesCollection())
            {
                Publishers oldPublisherIdOfBookTitlesCollectionBookTitles = bookTitlesCollectionBookTitles.getPublisherId();
                bookTitlesCollectionBookTitles.setPublisherId(publishers);
                bookTitlesCollectionBookTitles = em.merge(bookTitlesCollectionBookTitles);
                if (oldPublisherIdOfBookTitlesCollectionBookTitles != null)
                {
                    oldPublisherIdOfBookTitlesCollectionBookTitles.getBookTitlesCollection().remove(bookTitlesCollectionBookTitles);
                    oldPublisherIdOfBookTitlesCollectionBookTitles = em.merge(oldPublisherIdOfBookTitlesCollectionBookTitles);
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

    public void edit(Publishers publishers) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Publishers persistentPublishers = em.find(Publishers.class, publishers.getId());
            Countries countryIdOld = persistentPublishers.getCountryId();
            Countries countryIdNew = publishers.getCountryId();
            Collection<BookTitles> bookTitlesCollectionOld = persistentPublishers.getBookTitlesCollection();
            Collection<BookTitles> bookTitlesCollectionNew = publishers.getBookTitlesCollection();
            if (countryIdNew != null)
            {
                countryIdNew = em.getReference(countryIdNew.getClass(), countryIdNew.getId());
                publishers.setCountryId(countryIdNew);
            }
            Collection<BookTitles> attachedBookTitlesCollectionNew = new ArrayList<BookTitles>();
            for (BookTitles bookTitlesCollectionNewBookTitlesToAttach : bookTitlesCollectionNew)
            {
                bookTitlesCollectionNewBookTitlesToAttach = em.getReference(bookTitlesCollectionNewBookTitlesToAttach.getClass(), bookTitlesCollectionNewBookTitlesToAttach.getId());
                attachedBookTitlesCollectionNew.add(bookTitlesCollectionNewBookTitlesToAttach);
            }
            bookTitlesCollectionNew = attachedBookTitlesCollectionNew;
            publishers.setBookTitlesCollection(bookTitlesCollectionNew);
            publishers = em.merge(publishers);
            if (countryIdOld != null && !countryIdOld.equals(countryIdNew))
            {
                countryIdOld.getPublishersCollection().remove(publishers);
                countryIdOld = em.merge(countryIdOld);
            }
            if (countryIdNew != null && !countryIdNew.equals(countryIdOld))
            {
                countryIdNew.getPublishersCollection().add(publishers);
                countryIdNew = em.merge(countryIdNew);
            }
            for (BookTitles bookTitlesCollectionOldBookTitles : bookTitlesCollectionOld)
            {
                if (!bookTitlesCollectionNew.contains(bookTitlesCollectionOldBookTitles))
                {
                    bookTitlesCollectionOldBookTitles.setPublisherId(null);
                    bookTitlesCollectionOldBookTitles = em.merge(bookTitlesCollectionOldBookTitles);
                }
            }
            for (BookTitles bookTitlesCollectionNewBookTitles : bookTitlesCollectionNew)
            {
                if (!bookTitlesCollectionOld.contains(bookTitlesCollectionNewBookTitles))
                {
                    Publishers oldPublisherIdOfBookTitlesCollectionNewBookTitles = bookTitlesCollectionNewBookTitles.getPublisherId();
                    bookTitlesCollectionNewBookTitles.setPublisherId(publishers);
                    bookTitlesCollectionNewBookTitles = em.merge(bookTitlesCollectionNewBookTitles);
                    if (oldPublisherIdOfBookTitlesCollectionNewBookTitles != null && !oldPublisherIdOfBookTitlesCollectionNewBookTitles.equals(publishers))
                    {
                        oldPublisherIdOfBookTitlesCollectionNewBookTitles.getBookTitlesCollection().remove(bookTitlesCollectionNewBookTitles);
                        oldPublisherIdOfBookTitlesCollectionNewBookTitles = em.merge(oldPublisherIdOfBookTitlesCollectionNewBookTitles);
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
                Integer id = publishers.getId();
                if (findPublishers(id) == null)
                {
                    throw new NonexistentEntityException("The publishers with id " + id + " no longer exists.");
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

    public void destroy(Integer id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Publishers publishers;
            try
            {
                publishers = em.getReference(Publishers.class, id);
                publishers.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The publishers with id " + id + " no longer exists.", enfe);
            }
            Countries countryId = publishers.getCountryId();
            if (countryId != null)
            {
                countryId.getPublishersCollection().remove(publishers);
                countryId = em.merge(countryId);
            }
            Collection<BookTitles> bookTitlesCollection = publishers.getBookTitlesCollection();
            for (BookTitles bookTitlesCollectionBookTitles : bookTitlesCollection)
            {
                bookTitlesCollectionBookTitles.setPublisherId(null);
                bookTitlesCollectionBookTitles = em.merge(bookTitlesCollectionBookTitles);
            }
            em.remove(publishers);
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

    public List<Publishers> findPublishersEntities()
    {
        return findPublishersEntities(true, -1, -1);
    }

    public List<Publishers> findPublishersEntities(int maxResults, int firstResult)
    {
        return findPublishersEntities(false, maxResults, firstResult);
    }

    private List<Publishers> findPublishersEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Publishers.class));
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

    public Publishers findPublishers(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Publishers.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getPublishersCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Publishers> rt = cq.from(Publishers.class);
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
