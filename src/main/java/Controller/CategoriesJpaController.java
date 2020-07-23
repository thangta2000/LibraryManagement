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
import Utility.Factory;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class CategoriesJpaController implements Serializable
{

//    public CategoriesJpaController(EntityManagerFactory emf)
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

    public void create(Categories categories)
    {
        if (categories.getBookTitlesList() == null)
        {
            categories.setBookTitlesList(new ArrayList<BookTitles>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<BookTitles> attachedBookTitlesList = new ArrayList<BookTitles>();
            for (BookTitles bookTitlesListBookTitlesToAttach : categories.getBookTitlesList())
            {
                bookTitlesListBookTitlesToAttach = em.getReference(bookTitlesListBookTitlesToAttach.getClass(), bookTitlesListBookTitlesToAttach.getId());
                attachedBookTitlesList.add(bookTitlesListBookTitlesToAttach);
            }
            categories.setBookTitlesList(attachedBookTitlesList);
            em.persist(categories);
            for (BookTitles bookTitlesListBookTitles : categories.getBookTitlesList())
            {
                Categories oldCategoryIdOfBookTitlesListBookTitles = bookTitlesListBookTitles.getCategoryId();
                bookTitlesListBookTitles.setCategoryId(categories);
                bookTitlesListBookTitles = em.merge(bookTitlesListBookTitles);
                if (oldCategoryIdOfBookTitlesListBookTitles != null)
                {
                    oldCategoryIdOfBookTitlesListBookTitles.getBookTitlesList().remove(bookTitlesListBookTitles);
                    oldCategoryIdOfBookTitlesListBookTitles = em.merge(oldCategoryIdOfBookTitlesListBookTitles);
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
            List<BookTitles> bookTitlesListOld = persistentCategories.getBookTitlesList();
            List<BookTitles> bookTitlesListNew = categories.getBookTitlesList();
            List<BookTitles> attachedBookTitlesListNew = new ArrayList<BookTitles>();
            for (BookTitles bookTitlesListNewBookTitlesToAttach : bookTitlesListNew)
            {
                bookTitlesListNewBookTitlesToAttach = em.getReference(bookTitlesListNewBookTitlesToAttach.getClass(), bookTitlesListNewBookTitlesToAttach.getId());
                attachedBookTitlesListNew.add(bookTitlesListNewBookTitlesToAttach);
            }
            bookTitlesListNew = attachedBookTitlesListNew;
            categories.setBookTitlesList(bookTitlesListNew);
            categories = em.merge(categories);
            for (BookTitles bookTitlesListOldBookTitles : bookTitlesListOld)
            {
                if (!bookTitlesListNew.contains(bookTitlesListOldBookTitles))
                {
                    bookTitlesListOldBookTitles.setCategoryId(null);
                    bookTitlesListOldBookTitles = em.merge(bookTitlesListOldBookTitles);
                }
            }
            for (BookTitles bookTitlesListNewBookTitles : bookTitlesListNew)
            {
                if (!bookTitlesListOld.contains(bookTitlesListNewBookTitles))
                {
                    Categories oldCategoryIdOfBookTitlesListNewBookTitles = bookTitlesListNewBookTitles.getCategoryId();
                    bookTitlesListNewBookTitles.setCategoryId(categories);
                    bookTitlesListNewBookTitles = em.merge(bookTitlesListNewBookTitles);
                    if (oldCategoryIdOfBookTitlesListNewBookTitles != null && !oldCategoryIdOfBookTitlesListNewBookTitles.equals(categories))
                    {
                        oldCategoryIdOfBookTitlesListNewBookTitles.getBookTitlesList().remove(bookTitlesListNewBookTitles);
                        oldCategoryIdOfBookTitlesListNewBookTitles = em.merge(oldCategoryIdOfBookTitlesListNewBookTitles);
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
            List<BookTitles> bookTitlesList = categories.getBookTitlesList();
            for (BookTitles bookTitlesListBookTitles : bookTitlesList)
            {
                bookTitlesListBookTitles.setCategoryId(null);
                bookTitlesListBookTitles = em.merge(bookTitlesListBookTitles);
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

    public static List<Categories> findCategoriesEntities()
    {
        return findCategoriesEntities(true, -1, -1);
    }

    public static List<Categories> findCategoriesEntities(int maxResults, int firstResult)
    {
        return findCategoriesEntities(false, maxResults, firstResult);
    }

    private static List<Categories> findCategoriesEntities(boolean all, int maxResults, int firstResult)
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
