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
import Model.BookTitles;
import Model.Categories;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class CategoriesJpaController implements Serializable
{

    public CategoriesJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Categories categories)
    {
        if (categories.getBookTitlesCollection() == null)
        {
            categories.setBookTitlesCollection(new ArrayList<BookTitles>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<BookTitles> attachedBookTitlesCollection = new ArrayList<BookTitles>();
            for (BookTitles bookTitlesCollectionBookTitlesToAttach : categories.getBookTitlesCollection())
            {
                bookTitlesCollectionBookTitlesToAttach = em.getReference(bookTitlesCollectionBookTitlesToAttach.getClass(), bookTitlesCollectionBookTitlesToAttach.getId());
                attachedBookTitlesCollection.add(bookTitlesCollectionBookTitlesToAttach);
            }
            categories.setBookTitlesCollection(attachedBookTitlesCollection);
            em.persist(categories);
            for (BookTitles bookTitlesCollectionBookTitles : categories.getBookTitlesCollection())
            {
                Categories oldCategoryIdOfBookTitlesCollectionBookTitles = bookTitlesCollectionBookTitles.getCategoryId();
                bookTitlesCollectionBookTitles.setCategoryId(categories);
                bookTitlesCollectionBookTitles = em.merge(bookTitlesCollectionBookTitles);
                if (oldCategoryIdOfBookTitlesCollectionBookTitles != null)
                {
                    oldCategoryIdOfBookTitlesCollectionBookTitles.getBookTitlesCollection().remove(bookTitlesCollectionBookTitles);
                    oldCategoryIdOfBookTitlesCollectionBookTitles = em.merge(oldCategoryIdOfBookTitlesCollectionBookTitles);
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

    public void edit(Categories categories) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Categories persistentCategories = em.find(Categories.class, categories.getId());
            Collection<BookTitles> bookTitlesCollectionOld = persistentCategories.getBookTitlesCollection();
            Collection<BookTitles> bookTitlesCollectionNew = categories.getBookTitlesCollection();
            Collection<BookTitles> attachedBookTitlesCollectionNew = new ArrayList<BookTitles>();
            for (BookTitles bookTitlesCollectionNewBookTitlesToAttach : bookTitlesCollectionNew)
            {
                bookTitlesCollectionNewBookTitlesToAttach = em.getReference(bookTitlesCollectionNewBookTitlesToAttach.getClass(), bookTitlesCollectionNewBookTitlesToAttach.getId());
                attachedBookTitlesCollectionNew.add(bookTitlesCollectionNewBookTitlesToAttach);
            }
            bookTitlesCollectionNew = attachedBookTitlesCollectionNew;
            categories.setBookTitlesCollection(bookTitlesCollectionNew);
            categories = em.merge(categories);
            for (BookTitles bookTitlesCollectionOldBookTitles : bookTitlesCollectionOld)
            {
                if (!bookTitlesCollectionNew.contains(bookTitlesCollectionOldBookTitles))
                {
                    bookTitlesCollectionOldBookTitles.setCategoryId(null);
                    bookTitlesCollectionOldBookTitles = em.merge(bookTitlesCollectionOldBookTitles);
                }
            }
            for (BookTitles bookTitlesCollectionNewBookTitles : bookTitlesCollectionNew)
            {
                if (!bookTitlesCollectionOld.contains(bookTitlesCollectionNewBookTitles))
                {
                    Categories oldCategoryIdOfBookTitlesCollectionNewBookTitles = bookTitlesCollectionNewBookTitles.getCategoryId();
                    bookTitlesCollectionNewBookTitles.setCategoryId(categories);
                    bookTitlesCollectionNewBookTitles = em.merge(bookTitlesCollectionNewBookTitles);
                    if (oldCategoryIdOfBookTitlesCollectionNewBookTitles != null && !oldCategoryIdOfBookTitlesCollectionNewBookTitles.equals(categories))
                    {
                        oldCategoryIdOfBookTitlesCollectionNewBookTitles.getBookTitlesCollection().remove(bookTitlesCollectionNewBookTitles);
                        oldCategoryIdOfBookTitlesCollectionNewBookTitles = em.merge(oldCategoryIdOfBookTitlesCollectionNewBookTitles);
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
                Integer id = categories.getId();
                if (findCategories(id) == null)
                {
                    throw new NonexistentEntityException("The categories with id " + id + " no longer exists.");
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
            Categories categories;
            try
            {
                categories = em.getReference(Categories.class, id);
                categories.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The categories with id " + id + " no longer exists.", enfe);
            }
            Collection<BookTitles> bookTitlesCollection = categories.getBookTitlesCollection();
            for (BookTitles bookTitlesCollectionBookTitles : bookTitlesCollection)
            {
                bookTitlesCollectionBookTitles.setCategoryId(null);
                bookTitlesCollectionBookTitles = em.merge(bookTitlesCollectionBookTitles);
            }
            em.remove(categories);
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

    public List<Categories> findCategoriesEntities()
    {
        return findCategoriesEntities(true, -1, -1);
    }

    public List<Categories> findCategoriesEntities(int maxResults, int firstResult)
    {
        return findCategoriesEntities(false, maxResults, firstResult);
    }

    private List<Categories> findCategoriesEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categories.class));
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

    public Categories findCategories(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Categories.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getCategoriesCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categories> rt = cq.from(Categories.class);
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
