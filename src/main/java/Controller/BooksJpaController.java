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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tkang_85a
 */
public class BooksJpaController implements Serializable {

    public BooksJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Books books) {
        if (books.getBorrowsList() == null) {
            books.setBorrowsList(new ArrayList<Borrows>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BookTitles bookTitleId = books.getBookTitleId();
            if (bookTitleId != null) {
                bookTitleId = em.getReference(bookTitleId.getClass(), bookTitleId.getId());
                books.setBookTitleId(bookTitleId);
            }
            List<Borrows> attachedBorrowsList = new ArrayList<Borrows>();
            for (Borrows borrowsListBorrowsToAttach : books.getBorrowsList()) {
                borrowsListBorrowsToAttach = em.getReference(borrowsListBorrowsToAttach.getClass(), borrowsListBorrowsToAttach.getId());
                attachedBorrowsList.add(borrowsListBorrowsToAttach);
            }
            books.setBorrowsList(attachedBorrowsList);
            em.persist(books);
            if (bookTitleId != null) {
                bookTitleId.getBooksList().add(books);
                bookTitleId = em.merge(bookTitleId);
            }
            for (Borrows borrowsListBorrows : books.getBorrowsList()) {
                Books oldBookIdOfBorrowsListBorrows = borrowsListBorrows.getBookId();
                borrowsListBorrows.setBookId(books);
                borrowsListBorrows = em.merge(borrowsListBorrows);
                if (oldBookIdOfBorrowsListBorrows != null) {
                    oldBookIdOfBorrowsListBorrows.getBorrowsList().remove(borrowsListBorrows);
                    oldBookIdOfBorrowsListBorrows = em.merge(oldBookIdOfBorrowsListBorrows);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Books books) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Books persistentBooks = em.find(Books.class, books.getId());
            BookTitles bookTitleIdOld = persistentBooks.getBookTitleId();
            BookTitles bookTitleIdNew = books.getBookTitleId();
            List<Borrows> borrowsListOld = persistentBooks.getBorrowsList();
            List<Borrows> borrowsListNew = books.getBorrowsList();
            if (bookTitleIdNew != null) {
                bookTitleIdNew = em.getReference(bookTitleIdNew.getClass(), bookTitleIdNew.getId());
                books.setBookTitleId(bookTitleIdNew);
            }
            List<Borrows> attachedBorrowsListNew = new ArrayList<Borrows>();
            for (Borrows borrowsListNewBorrowsToAttach : borrowsListNew) {
                borrowsListNewBorrowsToAttach = em.getReference(borrowsListNewBorrowsToAttach.getClass(), borrowsListNewBorrowsToAttach.getId());
                attachedBorrowsListNew.add(borrowsListNewBorrowsToAttach);
            }
            borrowsListNew = attachedBorrowsListNew;
            books.setBorrowsList(borrowsListNew);
            books = em.merge(books);
            if (bookTitleIdOld != null && !bookTitleIdOld.equals(bookTitleIdNew)) {
                bookTitleIdOld.getBooksList().remove(books);
                bookTitleIdOld = em.merge(bookTitleIdOld);
            }
            if (bookTitleIdNew != null && !bookTitleIdNew.equals(bookTitleIdOld)) {
                bookTitleIdNew.getBooksList().add(books);
                bookTitleIdNew = em.merge(bookTitleIdNew);
            }
            for (Borrows borrowsListOldBorrows : borrowsListOld) {
                if (!borrowsListNew.contains(borrowsListOldBorrows)) {
                    borrowsListOldBorrows.setBookId(null);
                    borrowsListOldBorrows = em.merge(borrowsListOldBorrows);
                }
            }
            for (Borrows borrowsListNewBorrows : borrowsListNew) {
                if (!borrowsListOld.contains(borrowsListNewBorrows)) {
                    Books oldBookIdOfBorrowsListNewBorrows = borrowsListNewBorrows.getBookId();
                    borrowsListNewBorrows.setBookId(books);
                    borrowsListNewBorrows = em.merge(borrowsListNewBorrows);
                    if (oldBookIdOfBorrowsListNewBorrows != null && !oldBookIdOfBorrowsListNewBorrows.equals(books)) {
                        oldBookIdOfBorrowsListNewBorrows.getBorrowsList().remove(borrowsListNewBorrows);
                        oldBookIdOfBorrowsListNewBorrows = em.merge(oldBookIdOfBorrowsListNewBorrows);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = books.getId();
                if (findBooks(id) == null) {
                    throw new NonexistentEntityException("The books with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Books books;
            try {
                books = em.getReference(Books.class, id);
                books.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The books with id " + id + " no longer exists.", enfe);
            }
            BookTitles bookTitleId = books.getBookTitleId();
            if (bookTitleId != null) {
                bookTitleId.getBooksList().remove(books);
                bookTitleId = em.merge(bookTitleId);
            }
            List<Borrows> borrowsList = books.getBorrowsList();
            for (Borrows borrowsListBorrows : borrowsList) {
                borrowsListBorrows.setBookId(null);
                borrowsListBorrows = em.merge(borrowsListBorrows);
            }
            em.remove(books);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Books> findBooksEntities() {
        return findBooksEntities(true, -1, -1);
    }

    public List<Books> findBooksEntities(int maxResults, int firstResult) {
        return findBooksEntities(false, maxResults, firstResult);
    }

    private List<Books> findBooksEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Books.class));
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

    public Books findBooks(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Books.class, id);
        } finally {
            em.close();
        }
    }

    public int getBooksCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Books> rt = cq.from(Books.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
