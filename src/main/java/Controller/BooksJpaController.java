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
import Model.Books;
import Model.Borrows;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class BooksJpaController implements Serializable
{

    public BooksJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Books books)
    {
        if (books.getBorrowsCollection() == null)
        {
            books.setBorrowsCollection(new ArrayList<Borrows>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            BookTitles bookTitleId = books.getBookTitleId();
            if (bookTitleId != null)
            {
                bookTitleId = em.getReference(bookTitleId.getClass(), bookTitleId.getId());
                books.setBookTitleId(bookTitleId);
            }
            Collection<Borrows> attachedBorrowsCollection = new ArrayList<Borrows>();
            for (Borrows borrowsCollectionBorrowsToAttach : books.getBorrowsCollection())
            {
                borrowsCollectionBorrowsToAttach = em.getReference(borrowsCollectionBorrowsToAttach.getClass(), borrowsCollectionBorrowsToAttach.getId());
                attachedBorrowsCollection.add(borrowsCollectionBorrowsToAttach);
            }
            books.setBorrowsCollection(attachedBorrowsCollection);
            em.persist(books);
            if (bookTitleId != null)
            {
                bookTitleId.getBooksCollection().add(books);
                bookTitleId = em.merge(bookTitleId);
            }
            for (Borrows borrowsCollectionBorrows : books.getBorrowsCollection())
            {
                Books oldBookIdOfBorrowsCollectionBorrows = borrowsCollectionBorrows.getBookId();
                borrowsCollectionBorrows.setBookId(books);
                borrowsCollectionBorrows = em.merge(borrowsCollectionBorrows);
                if (oldBookIdOfBorrowsCollectionBorrows != null)
                {
                    oldBookIdOfBorrowsCollectionBorrows.getBorrowsCollection().remove(borrowsCollectionBorrows);
                    oldBookIdOfBorrowsCollectionBorrows = em.merge(oldBookIdOfBorrowsCollectionBorrows);
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

    public void edit(Books books) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Books persistentBooks = em.find(Books.class, books.getId());
            BookTitles bookTitleIdOld = persistentBooks.getBookTitleId();
            BookTitles bookTitleIdNew = books.getBookTitleId();
            Collection<Borrows> borrowsCollectionOld = persistentBooks.getBorrowsCollection();
            Collection<Borrows> borrowsCollectionNew = books.getBorrowsCollection();
            if (bookTitleIdNew != null)
            {
                bookTitleIdNew = em.getReference(bookTitleIdNew.getClass(), bookTitleIdNew.getId());
                books.setBookTitleId(bookTitleIdNew);
            }
            Collection<Borrows> attachedBorrowsCollectionNew = new ArrayList<Borrows>();
            for (Borrows borrowsCollectionNewBorrowsToAttach : borrowsCollectionNew)
            {
                borrowsCollectionNewBorrowsToAttach = em.getReference(borrowsCollectionNewBorrowsToAttach.getClass(), borrowsCollectionNewBorrowsToAttach.getId());
                attachedBorrowsCollectionNew.add(borrowsCollectionNewBorrowsToAttach);
            }
            borrowsCollectionNew = attachedBorrowsCollectionNew;
            books.setBorrowsCollection(borrowsCollectionNew);
            books = em.merge(books);
            if (bookTitleIdOld != null && !bookTitleIdOld.equals(bookTitleIdNew))
            {
                bookTitleIdOld.getBooksCollection().remove(books);
                bookTitleIdOld = em.merge(bookTitleIdOld);
            }
            if (bookTitleIdNew != null && !bookTitleIdNew.equals(bookTitleIdOld))
            {
                bookTitleIdNew.getBooksCollection().add(books);
                bookTitleIdNew = em.merge(bookTitleIdNew);
            }
            for (Borrows borrowsCollectionOldBorrows : borrowsCollectionOld)
            {
                if (!borrowsCollectionNew.contains(borrowsCollectionOldBorrows))
                {
                    borrowsCollectionOldBorrows.setBookId(null);
                    borrowsCollectionOldBorrows = em.merge(borrowsCollectionOldBorrows);
                }
            }
            for (Borrows borrowsCollectionNewBorrows : borrowsCollectionNew)
            {
                if (!borrowsCollectionOld.contains(borrowsCollectionNewBorrows))
                {
                    Books oldBookIdOfBorrowsCollectionNewBorrows = borrowsCollectionNewBorrows.getBookId();
                    borrowsCollectionNewBorrows.setBookId(books);
                    borrowsCollectionNewBorrows = em.merge(borrowsCollectionNewBorrows);
                    if (oldBookIdOfBorrowsCollectionNewBorrows != null && !oldBookIdOfBorrowsCollectionNewBorrows.equals(books))
                    {
                        oldBookIdOfBorrowsCollectionNewBorrows.getBorrowsCollection().remove(borrowsCollectionNewBorrows);
                        oldBookIdOfBorrowsCollectionNewBorrows = em.merge(oldBookIdOfBorrowsCollectionNewBorrows);
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
                Long id = books.getId();
                if (findBooks(id) == null)
                {
                    throw new NonexistentEntityException("The books with id " + id + " no longer exists.");
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
            Books books;
            try
            {
                books = em.getReference(Books.class, id);
                books.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The books with id " + id + " no longer exists.", enfe);
            }
            BookTitles bookTitleId = books.getBookTitleId();
            if (bookTitleId != null)
            {
                bookTitleId.getBooksCollection().remove(books);
                bookTitleId = em.merge(bookTitleId);
            }
            Collection<Borrows> borrowsCollection = books.getBorrowsCollection();
            for (Borrows borrowsCollectionBorrows : borrowsCollection)
            {
                borrowsCollectionBorrows.setBookId(null);
                borrowsCollectionBorrows = em.merge(borrowsCollectionBorrows);
            }
            em.remove(books);
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

    public List<Books> findBooksEntities()
    {
        return findBooksEntities(true, -1, -1);
    }

    public List<Books> findBooksEntities(int maxResults, int firstResult)
    {
        return findBooksEntities(false, maxResults, firstResult);
    }

    private List<Books> findBooksEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Books.class));
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

    public Books findBooks(Long id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Books.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getBooksCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Books> rt = cq.from(Books.class);
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
