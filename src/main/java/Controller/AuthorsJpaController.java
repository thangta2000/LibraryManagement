/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.Authors;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Countries;
import Model.BooksByAuthors;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class AuthorsJpaController implements Serializable
{

    public AuthorsJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Authors authors)
    {
        if (authors.getBooksByAuthorsCollection() == null)
        {
            authors.setBooksByAuthorsCollection(new ArrayList<BooksByAuthors>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Countries countryId = authors.getCountryId();
            if (countryId != null)
            {
                countryId = em.getReference(countryId.getClass(), countryId.getId());
                authors.setCountryId(countryId);
            }
            Collection<BooksByAuthors> attachedBooksByAuthorsCollection = new ArrayList<BooksByAuthors>();
            for (BooksByAuthors booksByAuthorsCollectionBooksByAuthorsToAttach : authors.getBooksByAuthorsCollection())
            {
                booksByAuthorsCollectionBooksByAuthorsToAttach = em.getReference(booksByAuthorsCollectionBooksByAuthorsToAttach.getClass(), booksByAuthorsCollectionBooksByAuthorsToAttach.getId());
                attachedBooksByAuthorsCollection.add(booksByAuthorsCollectionBooksByAuthorsToAttach);
            }
            authors.setBooksByAuthorsCollection(attachedBooksByAuthorsCollection);
            em.persist(authors);
            if (countryId != null)
            {
                countryId.getAuthorsCollection().add(authors);
                countryId = em.merge(countryId);
            }
            for (BooksByAuthors booksByAuthorsCollectionBooksByAuthors : authors.getBooksByAuthorsCollection())
            {
                Authors oldAuthorIdOfBooksByAuthorsCollectionBooksByAuthors = booksByAuthorsCollectionBooksByAuthors.getAuthorId();
                booksByAuthorsCollectionBooksByAuthors.setAuthorId(authors);
                booksByAuthorsCollectionBooksByAuthors = em.merge(booksByAuthorsCollectionBooksByAuthors);
                if (oldAuthorIdOfBooksByAuthorsCollectionBooksByAuthors != null)
                {
                    oldAuthorIdOfBooksByAuthorsCollectionBooksByAuthors.getBooksByAuthorsCollection().remove(booksByAuthorsCollectionBooksByAuthors);
                    oldAuthorIdOfBooksByAuthorsCollectionBooksByAuthors = em.merge(oldAuthorIdOfBooksByAuthorsCollectionBooksByAuthors);
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

    public void edit(Authors authors) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Authors persistentAuthors = em.find(Authors.class, authors.getId());
            Countries countryIdOld = persistentAuthors.getCountryId();
            Countries countryIdNew = authors.getCountryId();
            Collection<BooksByAuthors> booksByAuthorsCollectionOld = persistentAuthors.getBooksByAuthorsCollection();
            Collection<BooksByAuthors> booksByAuthorsCollectionNew = authors.getBooksByAuthorsCollection();
            if (countryIdNew != null)
            {
                countryIdNew = em.getReference(countryIdNew.getClass(), countryIdNew.getId());
                authors.setCountryId(countryIdNew);
            }
            Collection<BooksByAuthors> attachedBooksByAuthorsCollectionNew = new ArrayList<BooksByAuthors>();
            for (BooksByAuthors booksByAuthorsCollectionNewBooksByAuthorsToAttach : booksByAuthorsCollectionNew)
            {
                booksByAuthorsCollectionNewBooksByAuthorsToAttach = em.getReference(booksByAuthorsCollectionNewBooksByAuthorsToAttach.getClass(), booksByAuthorsCollectionNewBooksByAuthorsToAttach.getId());
                attachedBooksByAuthorsCollectionNew.add(booksByAuthorsCollectionNewBooksByAuthorsToAttach);
            }
            booksByAuthorsCollectionNew = attachedBooksByAuthorsCollectionNew;
            authors.setBooksByAuthorsCollection(booksByAuthorsCollectionNew);
            authors = em.merge(authors);
            if (countryIdOld != null && !countryIdOld.equals(countryIdNew))
            {
                countryIdOld.getAuthorsCollection().remove(authors);
                countryIdOld = em.merge(countryIdOld);
            }
            if (countryIdNew != null && !countryIdNew.equals(countryIdOld))
            {
                countryIdNew.getAuthorsCollection().add(authors);
                countryIdNew = em.merge(countryIdNew);
            }
            for (BooksByAuthors booksByAuthorsCollectionOldBooksByAuthors : booksByAuthorsCollectionOld)
            {
                if (!booksByAuthorsCollectionNew.contains(booksByAuthorsCollectionOldBooksByAuthors))
                {
                    booksByAuthorsCollectionOldBooksByAuthors.setAuthorId(null);
                    booksByAuthorsCollectionOldBooksByAuthors = em.merge(booksByAuthorsCollectionOldBooksByAuthors);
                }
            }
            for (BooksByAuthors booksByAuthorsCollectionNewBooksByAuthors : booksByAuthorsCollectionNew)
            {
                if (!booksByAuthorsCollectionOld.contains(booksByAuthorsCollectionNewBooksByAuthors))
                {
                    Authors oldAuthorIdOfBooksByAuthorsCollectionNewBooksByAuthors = booksByAuthorsCollectionNewBooksByAuthors.getAuthorId();
                    booksByAuthorsCollectionNewBooksByAuthors.setAuthorId(authors);
                    booksByAuthorsCollectionNewBooksByAuthors = em.merge(booksByAuthorsCollectionNewBooksByAuthors);
                    if (oldAuthorIdOfBooksByAuthorsCollectionNewBooksByAuthors != null && !oldAuthorIdOfBooksByAuthorsCollectionNewBooksByAuthors.equals(authors))
                    {
                        oldAuthorIdOfBooksByAuthorsCollectionNewBooksByAuthors.getBooksByAuthorsCollection().remove(booksByAuthorsCollectionNewBooksByAuthors);
                        oldAuthorIdOfBooksByAuthorsCollectionNewBooksByAuthors = em.merge(oldAuthorIdOfBooksByAuthorsCollectionNewBooksByAuthors);
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
                Integer id = authors.getId();
                if (findAuthors(id) == null)
                {
                    throw new NonexistentEntityException("The authors with id " + id + " no longer exists.");
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
            Authors authors;
            try
            {
                authors = em.getReference(Authors.class, id);
                authors.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The authors with id " + id + " no longer exists.", enfe);
            }
            Countries countryId = authors.getCountryId();
            if (countryId != null)
            {
                countryId.getAuthorsCollection().remove(authors);
                countryId = em.merge(countryId);
            }
            Collection<BooksByAuthors> booksByAuthorsCollection = authors.getBooksByAuthorsCollection();
            for (BooksByAuthors booksByAuthorsCollectionBooksByAuthors : booksByAuthorsCollection)
            {
                booksByAuthorsCollectionBooksByAuthors.setAuthorId(null);
                booksByAuthorsCollectionBooksByAuthors = em.merge(booksByAuthorsCollectionBooksByAuthors);
            }
            em.remove(authors);
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

    public List<Authors> findAuthorsEntities()
    {
        return findAuthorsEntities(true, -1, -1);
    }

    public List<Authors> findAuthorsEntities(int maxResults, int firstResult)
    {
        return findAuthorsEntities(false, maxResults, firstResult);
    }

    private List<Authors> findAuthorsEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Authors.class));
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

    public Authors findAuthors(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Authors.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getAuthorsCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Authors> rt = cq.from(Authors.class);
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
