/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.BookTitles;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Categories;
import Model.Countries;
import Model.Publishers;
import Model.Books;
import java.util.ArrayList;
import java.util.Collection;
import Model.BooksByAuthors;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class BookTitlesJpaController implements Serializable
{

    public BookTitlesJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(BookTitles bookTitles)
    {
        if (bookTitles.getBooksCollection() == null)
        {
            bookTitles.setBooksCollection(new ArrayList<Books>());
        }
        if (bookTitles.getBooksByAuthorsCollection() == null)
        {
            bookTitles.setBooksByAuthorsCollection(new ArrayList<BooksByAuthors>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Categories categoryId = bookTitles.getCategoryId();
            if (categoryId != null)
            {
                categoryId = em.getReference(categoryId.getClass(), categoryId.getId());
                bookTitles.setCategoryId(categoryId);
            }
            Countries countryId = bookTitles.getCountryId();
            if (countryId != null)
            {
                countryId = em.getReference(countryId.getClass(), countryId.getId());
                bookTitles.setCountryId(countryId);
            }
            Publishers publisherId = bookTitles.getPublisherId();
            if (publisherId != null)
            {
                publisherId = em.getReference(publisherId.getClass(), publisherId.getId());
                bookTitles.setPublisherId(publisherId);
            }
            Collection<Books> attachedBooksCollection = new ArrayList<Books>();
            for (Books booksCollectionBooksToAttach : bookTitles.getBooksCollection())
            {
                booksCollectionBooksToAttach = em.getReference(booksCollectionBooksToAttach.getClass(), booksCollectionBooksToAttach.getId());
                attachedBooksCollection.add(booksCollectionBooksToAttach);
            }
            bookTitles.setBooksCollection(attachedBooksCollection);
            Collection<BooksByAuthors> attachedBooksByAuthorsCollection = new ArrayList<BooksByAuthors>();
            for (BooksByAuthors booksByAuthorsCollectionBooksByAuthorsToAttach : bookTitles.getBooksByAuthorsCollection())
            {
                booksByAuthorsCollectionBooksByAuthorsToAttach = em.getReference(booksByAuthorsCollectionBooksByAuthorsToAttach.getClass(), booksByAuthorsCollectionBooksByAuthorsToAttach.getId());
                attachedBooksByAuthorsCollection.add(booksByAuthorsCollectionBooksByAuthorsToAttach);
            }
            bookTitles.setBooksByAuthorsCollection(attachedBooksByAuthorsCollection);
            em.persist(bookTitles);
            if (categoryId != null)
            {
                categoryId.getBookTitlesCollection().add(bookTitles);
                categoryId = em.merge(categoryId);
            }
            if (countryId != null)
            {
                countryId.getBookTitlesCollection().add(bookTitles);
                countryId = em.merge(countryId);
            }
            if (publisherId != null)
            {
                publisherId.getBookTitlesCollection().add(bookTitles);
                publisherId = em.merge(publisherId);
            }
            for (Books booksCollectionBooks : bookTitles.getBooksCollection())
            {
                BookTitles oldBookTitleIdOfBooksCollectionBooks = booksCollectionBooks.getBookTitleId();
                booksCollectionBooks.setBookTitleId(bookTitles);
                booksCollectionBooks = em.merge(booksCollectionBooks);
                if (oldBookTitleIdOfBooksCollectionBooks != null)
                {
                    oldBookTitleIdOfBooksCollectionBooks.getBooksCollection().remove(booksCollectionBooks);
                    oldBookTitleIdOfBooksCollectionBooks = em.merge(oldBookTitleIdOfBooksCollectionBooks);
                }
            }
            for (BooksByAuthors booksByAuthorsCollectionBooksByAuthors : bookTitles.getBooksByAuthorsCollection())
            {
                BookTitles oldBookTitleIdOfBooksByAuthorsCollectionBooksByAuthors = booksByAuthorsCollectionBooksByAuthors.getBookTitleId();
                booksByAuthorsCollectionBooksByAuthors.setBookTitleId(bookTitles);
                booksByAuthorsCollectionBooksByAuthors = em.merge(booksByAuthorsCollectionBooksByAuthors);
                if (oldBookTitleIdOfBooksByAuthorsCollectionBooksByAuthors != null)
                {
                    oldBookTitleIdOfBooksByAuthorsCollectionBooksByAuthors.getBooksByAuthorsCollection().remove(booksByAuthorsCollectionBooksByAuthors);
                    oldBookTitleIdOfBooksByAuthorsCollectionBooksByAuthors = em.merge(oldBookTitleIdOfBooksByAuthorsCollectionBooksByAuthors);
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

    public void edit(BookTitles bookTitles) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            BookTitles persistentBookTitles = em.find(BookTitles.class, bookTitles.getId());
            Categories categoryIdOld = persistentBookTitles.getCategoryId();
            Categories categoryIdNew = bookTitles.getCategoryId();
            Countries countryIdOld = persistentBookTitles.getCountryId();
            Countries countryIdNew = bookTitles.getCountryId();
            Publishers publisherIdOld = persistentBookTitles.getPublisherId();
            Publishers publisherIdNew = bookTitles.getPublisherId();
            Collection<Books> booksCollectionOld = persistentBookTitles.getBooksCollection();
            Collection<Books> booksCollectionNew = bookTitles.getBooksCollection();
            Collection<BooksByAuthors> booksByAuthorsCollectionOld = persistentBookTitles.getBooksByAuthorsCollection();
            Collection<BooksByAuthors> booksByAuthorsCollectionNew = bookTitles.getBooksByAuthorsCollection();
            if (categoryIdNew != null)
            {
                categoryIdNew = em.getReference(categoryIdNew.getClass(), categoryIdNew.getId());
                bookTitles.setCategoryId(categoryIdNew);
            }
            if (countryIdNew != null)
            {
                countryIdNew = em.getReference(countryIdNew.getClass(), countryIdNew.getId());
                bookTitles.setCountryId(countryIdNew);
            }
            if (publisherIdNew != null)
            {
                publisherIdNew = em.getReference(publisherIdNew.getClass(), publisherIdNew.getId());
                bookTitles.setPublisherId(publisherIdNew);
            }
            Collection<Books> attachedBooksCollectionNew = new ArrayList<Books>();
            for (Books booksCollectionNewBooksToAttach : booksCollectionNew)
            {
                booksCollectionNewBooksToAttach = em.getReference(booksCollectionNewBooksToAttach.getClass(), booksCollectionNewBooksToAttach.getId());
                attachedBooksCollectionNew.add(booksCollectionNewBooksToAttach);
            }
            booksCollectionNew = attachedBooksCollectionNew;
            bookTitles.setBooksCollection(booksCollectionNew);
            Collection<BooksByAuthors> attachedBooksByAuthorsCollectionNew = new ArrayList<BooksByAuthors>();
            for (BooksByAuthors booksByAuthorsCollectionNewBooksByAuthorsToAttach : booksByAuthorsCollectionNew)
            {
                booksByAuthorsCollectionNewBooksByAuthorsToAttach = em.getReference(booksByAuthorsCollectionNewBooksByAuthorsToAttach.getClass(), booksByAuthorsCollectionNewBooksByAuthorsToAttach.getId());
                attachedBooksByAuthorsCollectionNew.add(booksByAuthorsCollectionNewBooksByAuthorsToAttach);
            }
            booksByAuthorsCollectionNew = attachedBooksByAuthorsCollectionNew;
            bookTitles.setBooksByAuthorsCollection(booksByAuthorsCollectionNew);
            bookTitles = em.merge(bookTitles);
            if (categoryIdOld != null && !categoryIdOld.equals(categoryIdNew))
            {
                categoryIdOld.getBookTitlesCollection().remove(bookTitles);
                categoryIdOld = em.merge(categoryIdOld);
            }
            if (categoryIdNew != null && !categoryIdNew.equals(categoryIdOld))
            {
                categoryIdNew.getBookTitlesCollection().add(bookTitles);
                categoryIdNew = em.merge(categoryIdNew);
            }
            if (countryIdOld != null && !countryIdOld.equals(countryIdNew))
            {
                countryIdOld.getBookTitlesCollection().remove(bookTitles);
                countryIdOld = em.merge(countryIdOld);
            }
            if (countryIdNew != null && !countryIdNew.equals(countryIdOld))
            {
                countryIdNew.getBookTitlesCollection().add(bookTitles);
                countryIdNew = em.merge(countryIdNew);
            }
            if (publisherIdOld != null && !publisherIdOld.equals(publisherIdNew))
            {
                publisherIdOld.getBookTitlesCollection().remove(bookTitles);
                publisherIdOld = em.merge(publisherIdOld);
            }
            if (publisherIdNew != null && !publisherIdNew.equals(publisherIdOld))
            {
                publisherIdNew.getBookTitlesCollection().add(bookTitles);
                publisherIdNew = em.merge(publisherIdNew);
            }
            for (Books booksCollectionOldBooks : booksCollectionOld)
            {
                if (!booksCollectionNew.contains(booksCollectionOldBooks))
                {
                    booksCollectionOldBooks.setBookTitleId(null);
                    booksCollectionOldBooks = em.merge(booksCollectionOldBooks);
                }
            }
            for (Books booksCollectionNewBooks : booksCollectionNew)
            {
                if (!booksCollectionOld.contains(booksCollectionNewBooks))
                {
                    BookTitles oldBookTitleIdOfBooksCollectionNewBooks = booksCollectionNewBooks.getBookTitleId();
                    booksCollectionNewBooks.setBookTitleId(bookTitles);
                    booksCollectionNewBooks = em.merge(booksCollectionNewBooks);
                    if (oldBookTitleIdOfBooksCollectionNewBooks != null && !oldBookTitleIdOfBooksCollectionNewBooks.equals(bookTitles))
                    {
                        oldBookTitleIdOfBooksCollectionNewBooks.getBooksCollection().remove(booksCollectionNewBooks);
                        oldBookTitleIdOfBooksCollectionNewBooks = em.merge(oldBookTitleIdOfBooksCollectionNewBooks);
                    }
                }
            }
            for (BooksByAuthors booksByAuthorsCollectionOldBooksByAuthors : booksByAuthorsCollectionOld)
            {
                if (!booksByAuthorsCollectionNew.contains(booksByAuthorsCollectionOldBooksByAuthors))
                {
                    booksByAuthorsCollectionOldBooksByAuthors.setBookTitleId(null);
                    booksByAuthorsCollectionOldBooksByAuthors = em.merge(booksByAuthorsCollectionOldBooksByAuthors);
                }
            }
            for (BooksByAuthors booksByAuthorsCollectionNewBooksByAuthors : booksByAuthorsCollectionNew)
            {
                if (!booksByAuthorsCollectionOld.contains(booksByAuthorsCollectionNewBooksByAuthors))
                {
                    BookTitles oldBookTitleIdOfBooksByAuthorsCollectionNewBooksByAuthors = booksByAuthorsCollectionNewBooksByAuthors.getBookTitleId();
                    booksByAuthorsCollectionNewBooksByAuthors.setBookTitleId(bookTitles);
                    booksByAuthorsCollectionNewBooksByAuthors = em.merge(booksByAuthorsCollectionNewBooksByAuthors);
                    if (oldBookTitleIdOfBooksByAuthorsCollectionNewBooksByAuthors != null && !oldBookTitleIdOfBooksByAuthorsCollectionNewBooksByAuthors.equals(bookTitles))
                    {
                        oldBookTitleIdOfBooksByAuthorsCollectionNewBooksByAuthors.getBooksByAuthorsCollection().remove(booksByAuthorsCollectionNewBooksByAuthors);
                        oldBookTitleIdOfBooksByAuthorsCollectionNewBooksByAuthors = em.merge(oldBookTitleIdOfBooksByAuthorsCollectionNewBooksByAuthors);
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
                Integer id = bookTitles.getId();
                if (findBookTitles(id) == null)
                {
                    throw new NonexistentEntityException("The bookTitles with id " + id + " no longer exists.");
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
            BookTitles bookTitles;
            try
            {
                bookTitles = em.getReference(BookTitles.class, id);
                bookTitles.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The bookTitles with id " + id + " no longer exists.", enfe);
            }
            Categories categoryId = bookTitles.getCategoryId();
            if (categoryId != null)
            {
                categoryId.getBookTitlesCollection().remove(bookTitles);
                categoryId = em.merge(categoryId);
            }
            Countries countryId = bookTitles.getCountryId();
            if (countryId != null)
            {
                countryId.getBookTitlesCollection().remove(bookTitles);
                countryId = em.merge(countryId);
            }
            Publishers publisherId = bookTitles.getPublisherId();
            if (publisherId != null)
            {
                publisherId.getBookTitlesCollection().remove(bookTitles);
                publisherId = em.merge(publisherId);
            }
            Collection<Books> booksCollection = bookTitles.getBooksCollection();
            for (Books booksCollectionBooks : booksCollection)
            {
                booksCollectionBooks.setBookTitleId(null);
                booksCollectionBooks = em.merge(booksCollectionBooks);
            }
            Collection<BooksByAuthors> booksByAuthorsCollection = bookTitles.getBooksByAuthorsCollection();
            for (BooksByAuthors booksByAuthorsCollectionBooksByAuthors : booksByAuthorsCollection)
            {
                booksByAuthorsCollectionBooksByAuthors.setBookTitleId(null);
                booksByAuthorsCollectionBooksByAuthors = em.merge(booksByAuthorsCollectionBooksByAuthors);
            }
            em.remove(bookTitles);
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

    public List<BookTitles> findBookTitlesEntities()
    {
        return findBookTitlesEntities(true, -1, -1);
    }

    public List<BookTitles> findBookTitlesEntities(int maxResults, int firstResult)
    {
        return findBookTitlesEntities(false, maxResults, firstResult);
    }

    private List<BookTitles> findBookTitlesEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BookTitles.class));
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

    public BookTitles findBookTitles(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(BookTitles.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getBookTitlesCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BookTitles> rt = cq.from(BookTitles.class);
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
