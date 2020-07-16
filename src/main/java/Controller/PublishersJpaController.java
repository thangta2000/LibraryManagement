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
        if (publishers.getBookTitlesList() == null)
        {
            publishers.setBookTitlesList(new ArrayList<BookTitles>());
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
            List<BookTitles> attachedBookTitlesList = new ArrayList<BookTitles>();
            for (BookTitles bookTitlesListBookTitlesToAttach : publishers.getBookTitlesList())
            {
                bookTitlesListBookTitlesToAttach = em.getReference(bookTitlesListBookTitlesToAttach.getClass(), bookTitlesListBookTitlesToAttach.getId());
                attachedBookTitlesList.add(bookTitlesListBookTitlesToAttach);
            }
            publishers.setBookTitlesList(attachedBookTitlesList);
            em.persist(publishers);
            if (countryId != null)
            {
                countryId.getPublishersList().add(publishers);
                countryId = em.merge(countryId);
            }
            for (BookTitles bookTitlesListBookTitles : publishers.getBookTitlesList())
            {
                Publishers oldPublisherIdOfBookTitlesListBookTitles = bookTitlesListBookTitles.getPublisherId();
                bookTitlesListBookTitles.setPublisherId(publishers);
                bookTitlesListBookTitles = em.merge(bookTitlesListBookTitles);
                if (oldPublisherIdOfBookTitlesListBookTitles != null)
                {
                    oldPublisherIdOfBookTitlesListBookTitles.getBookTitlesList().remove(bookTitlesListBookTitles);
                    oldPublisherIdOfBookTitlesListBookTitles = em.merge(oldPublisherIdOfBookTitlesListBookTitles);
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
            List<BookTitles> bookTitlesListOld = persistentPublishers.getBookTitlesList();
            List<BookTitles> bookTitlesListNew = publishers.getBookTitlesList();
            if (countryIdNew != null)
            {
                countryIdNew = em.getReference(countryIdNew.getClass(), countryIdNew.getId());
                publishers.setCountryId(countryIdNew);
            }
            List<BookTitles> attachedBookTitlesListNew = new ArrayList<BookTitles>();
            for (BookTitles bookTitlesListNewBookTitlesToAttach : bookTitlesListNew)
            {
                bookTitlesListNewBookTitlesToAttach = em.getReference(bookTitlesListNewBookTitlesToAttach.getClass(), bookTitlesListNewBookTitlesToAttach.getId());
                attachedBookTitlesListNew.add(bookTitlesListNewBookTitlesToAttach);
            }
            bookTitlesListNew = attachedBookTitlesListNew;
            publishers.setBookTitlesList(bookTitlesListNew);
            publishers = em.merge(publishers);
            if (countryIdOld != null && !countryIdOld.equals(countryIdNew))
            {
                countryIdOld.getPublishersList().remove(publishers);
                countryIdOld = em.merge(countryIdOld);
            }
            if (countryIdNew != null && !countryIdNew.equals(countryIdOld))
            {
                countryIdNew.getPublishersList().add(publishers);
                countryIdNew = em.merge(countryIdNew);
            }
            for (BookTitles bookTitlesListOldBookTitles : bookTitlesListOld)
            {
                if (!bookTitlesListNew.contains(bookTitlesListOldBookTitles))
                {
                    bookTitlesListOldBookTitles.setPublisherId(null);
                    bookTitlesListOldBookTitles = em.merge(bookTitlesListOldBookTitles);
                }
            }
            for (BookTitles bookTitlesListNewBookTitles : bookTitlesListNew)
            {
                if (!bookTitlesListOld.contains(bookTitlesListNewBookTitles))
                {
                    Publishers oldPublisherIdOfBookTitlesListNewBookTitles = bookTitlesListNewBookTitles.getPublisherId();
                    bookTitlesListNewBookTitles.setPublisherId(publishers);
                    bookTitlesListNewBookTitles = em.merge(bookTitlesListNewBookTitles);
                    if (oldPublisherIdOfBookTitlesListNewBookTitles != null && !oldPublisherIdOfBookTitlesListNewBookTitles.equals(publishers))
                    {
                        oldPublisherIdOfBookTitlesListNewBookTitles.getBookTitlesList().remove(bookTitlesListNewBookTitles);
                        oldPublisherIdOfBookTitlesListNewBookTitles = em.merge(oldPublisherIdOfBookTitlesListNewBookTitles);
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
                countryId.getPublishersList().remove(publishers);
                countryId = em.merge(countryId);
            }
            List<BookTitles> bookTitlesList = publishers.getBookTitlesList();
            for (BookTitles bookTitlesListBookTitles : bookTitlesList)
            {
                bookTitlesListBookTitles.setPublisherId(null);
                bookTitlesListBookTitles = em.merge(bookTitlesListBookTitles);
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
