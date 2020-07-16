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
import Utility.Factory;
import java.util.List;
import Model.BooksByAuthors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tkang_85a
 */
public class BookTitlesJpaController implements Serializable {

//    public BookTitlesJpaController(EntityManagerFactory emf)
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

    public static void create(BookTitles bookTitles)
    {
        if (bookTitles.getBooksCollection() == null)
        {
            bookTitles.setBooksCollection(new ArrayList<Books>());
        }
        if (bookTitles.getBooksByAuthorsList() == null) {
            bookTitles.setBooksByAuthorsList(new ArrayList<BooksByAuthors>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categories categoryId = bookTitles.getCategoryId();
            if (categoryId != null) {
                categoryId = em.getReference(categoryId.getClass(), categoryId.getId());
                bookTitles.setCategoryId(categoryId);
            }
            Countries countryId = bookTitles.getCountryId();
            if (countryId != null) {
                countryId = em.getReference(countryId.getClass(), countryId.getId());
                bookTitles.setCountryId(countryId);
            }
            Publishers publisherId = bookTitles.getPublisherId();
            if (publisherId != null) {
                publisherId = em.getReference(publisherId.getClass(), publisherId.getId());
                bookTitles.setPublisherId(publisherId);
            }
            List<Books> attachedBooksList = new ArrayList<Books>();
            for (Books booksListBooksToAttach : bookTitles.getBooksList()) {
                booksListBooksToAttach = em.getReference(booksListBooksToAttach.getClass(), booksListBooksToAttach.getId());
                attachedBooksList.add(booksListBooksToAttach);
            }
            bookTitles.setBooksList(attachedBooksList);
            List<BooksByAuthors> attachedBooksByAuthorsList = new ArrayList<BooksByAuthors>();
            for (BooksByAuthors booksByAuthorsListBooksByAuthorsToAttach : bookTitles.getBooksByAuthorsList()) {
                booksByAuthorsListBooksByAuthorsToAttach = em.getReference(booksByAuthorsListBooksByAuthorsToAttach.getClass(), booksByAuthorsListBooksByAuthorsToAttach.getId());
                attachedBooksByAuthorsList.add(booksByAuthorsListBooksByAuthorsToAttach);
            }
            bookTitles.setBooksByAuthorsList(attachedBooksByAuthorsList);
            em.persist(bookTitles);
            if (categoryId != null) {
                categoryId.getBookTitlesList().add(bookTitles);
                categoryId = em.merge(categoryId);
            }
            if (countryId != null) {
                countryId.getBookTitlesList().add(bookTitles);
                countryId = em.merge(countryId);
            }
            if (publisherId != null) {
                publisherId.getBookTitlesList().add(bookTitles);
                publisherId = em.merge(publisherId);
            }
            for (Books booksListBooks : bookTitles.getBooksList()) {
                BookTitles oldBookTitleIdOfBooksListBooks = booksListBooks.getBookTitleId();
                booksListBooks.setBookTitleId(bookTitles);
                booksListBooks = em.merge(booksListBooks);
                if (oldBookTitleIdOfBooksListBooks != null) {
                    oldBookTitleIdOfBooksListBooks.getBooksList().remove(booksListBooks);
                    oldBookTitleIdOfBooksListBooks = em.merge(oldBookTitleIdOfBooksListBooks);
                }
            }
            for (BooksByAuthors booksByAuthorsListBooksByAuthors : bookTitles.getBooksByAuthorsList()) {
                BookTitles oldBookTitleIdOfBooksByAuthorsListBooksByAuthors = booksByAuthorsListBooksByAuthors.getBookTitleId();
                booksByAuthorsListBooksByAuthors.setBookTitleId(bookTitles);
                booksByAuthorsListBooksByAuthors = em.merge(booksByAuthorsListBooksByAuthors);
                if (oldBookTitleIdOfBooksByAuthorsListBooksByAuthors != null) {
                    oldBookTitleIdOfBooksByAuthorsListBooksByAuthors.getBooksByAuthorsList().remove(booksByAuthorsListBooksByAuthors);
                    oldBookTitleIdOfBooksByAuthorsListBooksByAuthors = em.merge(oldBookTitleIdOfBooksByAuthorsListBooksByAuthors);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void edit(BookTitles bookTitles) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BookTitles persistentBookTitles = em.find(BookTitles.class, bookTitles.getId());
            Categories categoryIdOld = persistentBookTitles.getCategoryId();
            Categories categoryIdNew = bookTitles.getCategoryId();
            Countries countryIdOld = persistentBookTitles.getCountryId();
            Countries countryIdNew = bookTitles.getCountryId();
            Publishers publisherIdOld = persistentBookTitles.getPublisherId();
            Publishers publisherIdNew = bookTitles.getPublisherId();
            List<Books> booksListOld = persistentBookTitles.getBooksList();
            List<Books> booksListNew = bookTitles.getBooksList();
            List<BooksByAuthors> booksByAuthorsListOld = persistentBookTitles.getBooksByAuthorsList();
            List<BooksByAuthors> booksByAuthorsListNew = bookTitles.getBooksByAuthorsList();
            if (categoryIdNew != null) {
                categoryIdNew = em.getReference(categoryIdNew.getClass(), categoryIdNew.getId());
                bookTitles.setCategoryId(categoryIdNew);
            }
            if (countryIdNew != null) {
                countryIdNew = em.getReference(countryIdNew.getClass(), countryIdNew.getId());
                bookTitles.setCountryId(countryIdNew);
            }
            if (publisherIdNew != null) {
                publisherIdNew = em.getReference(publisherIdNew.getClass(), publisherIdNew.getId());
                bookTitles.setPublisherId(publisherIdNew);
            }
            List<Books> attachedBooksListNew = new ArrayList<Books>();
            for (Books booksListNewBooksToAttach : booksListNew) {
                booksListNewBooksToAttach = em.getReference(booksListNewBooksToAttach.getClass(), booksListNewBooksToAttach.getId());
                attachedBooksListNew.add(booksListNewBooksToAttach);
            }
            booksListNew = attachedBooksListNew;
            bookTitles.setBooksList(booksListNew);
            List<BooksByAuthors> attachedBooksByAuthorsListNew = new ArrayList<BooksByAuthors>();
            for (BooksByAuthors booksByAuthorsListNewBooksByAuthorsToAttach : booksByAuthorsListNew) {
                booksByAuthorsListNewBooksByAuthorsToAttach = em.getReference(booksByAuthorsListNewBooksByAuthorsToAttach.getClass(), booksByAuthorsListNewBooksByAuthorsToAttach.getId());
                attachedBooksByAuthorsListNew.add(booksByAuthorsListNewBooksByAuthorsToAttach);
            }
            booksByAuthorsListNew = attachedBooksByAuthorsListNew;
            bookTitles.setBooksByAuthorsList(booksByAuthorsListNew);
            bookTitles = em.merge(bookTitles);
            if (categoryIdOld != null && !categoryIdOld.equals(categoryIdNew)) {
                categoryIdOld.getBookTitlesList().remove(bookTitles);
                categoryIdOld = em.merge(categoryIdOld);
            }
            if (categoryIdNew != null && !categoryIdNew.equals(categoryIdOld)) {
                categoryIdNew.getBookTitlesList().add(bookTitles);
                categoryIdNew = em.merge(categoryIdNew);
            }
            if (countryIdOld != null && !countryIdOld.equals(countryIdNew)) {
                countryIdOld.getBookTitlesList().remove(bookTitles);
                countryIdOld = em.merge(countryIdOld);
            }
            if (countryIdNew != null && !countryIdNew.equals(countryIdOld)) {
                countryIdNew.getBookTitlesList().add(bookTitles);
                countryIdNew = em.merge(countryIdNew);
            }
            if (publisherIdOld != null && !publisherIdOld.equals(publisherIdNew)) {
                publisherIdOld.getBookTitlesList().remove(bookTitles);
                publisherIdOld = em.merge(publisherIdOld);
            }
            if (publisherIdNew != null && !publisherIdNew.equals(publisherIdOld)) {
                publisherIdNew.getBookTitlesList().add(bookTitles);
                publisherIdNew = em.merge(publisherIdNew);
            }
            for (Books booksListOldBooks : booksListOld) {
                if (!booksListNew.contains(booksListOldBooks)) {
                    booksListOldBooks.setBookTitleId(null);
                    booksListOldBooks = em.merge(booksListOldBooks);
                }
            }
            for (Books booksListNewBooks : booksListNew) {
                if (!booksListOld.contains(booksListNewBooks)) {
                    BookTitles oldBookTitleIdOfBooksListNewBooks = booksListNewBooks.getBookTitleId();
                    booksListNewBooks.setBookTitleId(bookTitles);
                    booksListNewBooks = em.merge(booksListNewBooks);
                    if (oldBookTitleIdOfBooksListNewBooks != null && !oldBookTitleIdOfBooksListNewBooks.equals(bookTitles)) {
                        oldBookTitleIdOfBooksListNewBooks.getBooksList().remove(booksListNewBooks);
                        oldBookTitleIdOfBooksListNewBooks = em.merge(oldBookTitleIdOfBooksListNewBooks);
                    }
                }
            }
            for (BooksByAuthors booksByAuthorsListOldBooksByAuthors : booksByAuthorsListOld) {
                if (!booksByAuthorsListNew.contains(booksByAuthorsListOldBooksByAuthors)) {
                    booksByAuthorsListOldBooksByAuthors.setBookTitleId(null);
                    booksByAuthorsListOldBooksByAuthors = em.merge(booksByAuthorsListOldBooksByAuthors);
                }
            }
            for (BooksByAuthors booksByAuthorsListNewBooksByAuthors : booksByAuthorsListNew) {
                if (!booksByAuthorsListOld.contains(booksByAuthorsListNewBooksByAuthors)) {
                    BookTitles oldBookTitleIdOfBooksByAuthorsListNewBooksByAuthors = booksByAuthorsListNewBooksByAuthors.getBookTitleId();
                    booksByAuthorsListNewBooksByAuthors.setBookTitleId(bookTitles);
                    booksByAuthorsListNewBooksByAuthors = em.merge(booksByAuthorsListNewBooksByAuthors);
                    if (oldBookTitleIdOfBooksByAuthorsListNewBooksByAuthors != null && !oldBookTitleIdOfBooksByAuthorsListNewBooksByAuthors.equals(bookTitles)) {
                        oldBookTitleIdOfBooksByAuthorsListNewBooksByAuthors.getBooksByAuthorsList().remove(booksByAuthorsListNewBooksByAuthors);
                        oldBookTitleIdOfBooksByAuthorsListNewBooksByAuthors = em.merge(oldBookTitleIdOfBooksByAuthorsListNewBooksByAuthors);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bookTitles.getId();
                if (findBookTitles(id) == null) {
                    throw new NonexistentEntityException("The bookTitles with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void destroy(Integer id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BookTitles bookTitles;
            try {
                bookTitles = em.getReference(BookTitles.class, id);
                bookTitles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bookTitles with id " + id + " no longer exists.", enfe);
            }
            Categories categoryId = bookTitles.getCategoryId();
            if (categoryId != null) {
                categoryId.getBookTitlesList().remove(bookTitles);
                categoryId = em.merge(categoryId);
            }
            Countries countryId = bookTitles.getCountryId();
            if (countryId != null) {
                countryId.getBookTitlesList().remove(bookTitles);
                countryId = em.merge(countryId);
            }
            Publishers publisherId = bookTitles.getPublisherId();
            if (publisherId != null) {
                publisherId.getBookTitlesList().remove(bookTitles);
                publisherId = em.merge(publisherId);
            }
            List<Books> booksList = bookTitles.getBooksList();
            for (Books booksListBooks : booksList) {
                booksListBooks.setBookTitleId(null);
                booksListBooks = em.merge(booksListBooks);
            }
            List<BooksByAuthors> booksByAuthorsList = bookTitles.getBooksByAuthorsList();
            for (BooksByAuthors booksByAuthorsListBooksByAuthors : booksByAuthorsList) {
                booksByAuthorsListBooksByAuthors.setBookTitleId(null);
                booksByAuthorsListBooksByAuthors = em.merge(booksByAuthorsListBooksByAuthors);
            }
            em.remove(bookTitles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static List<BookTitles> findBookTitlesEntities()
    {
        return findBookTitlesEntities(true, -1, -1);
    }

    public static List<BookTitles> findBookTitlesEntities(int maxResults, int firstResult)
    {
        return findBookTitlesEntities(false, maxResults, firstResult);
    }

    private static List<BookTitles> findBookTitlesEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BookTitles.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public static BookTitles findBookTitles(Integer id)
    {
        EntityManager em = getEntityManager();
        try {
            return em.find(BookTitles.class, id);
        } finally {
            em.close();
        }
    }

    public static int getBookTitlesCount()
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BookTitles> rt = cq.from(BookTitles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
