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
import Model.Authors;
import Model.BookTitles;
import Model.BooksByAuthors;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tkang_85a
 */
public class BooksByAuthorsJpaController implements Serializable {

    public BooksByAuthorsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BooksByAuthors booksByAuthors) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Authors authorId = booksByAuthors.getAuthorId();
            if (authorId != null) {
                authorId = em.getReference(authorId.getClass(), authorId.getId());
                booksByAuthors.setAuthorId(authorId);
            }
            BookTitles bookTitleId = booksByAuthors.getBookTitleId();
            if (bookTitleId != null) {
                bookTitleId = em.getReference(bookTitleId.getClass(), bookTitleId.getId());
                booksByAuthors.setBookTitleId(bookTitleId);
            }
            em.persist(booksByAuthors);
            if (authorId != null) {
                authorId.getBooksByAuthorsList().add(booksByAuthors);
                authorId = em.merge(authorId);
            }
            if (bookTitleId != null) {
                bookTitleId.getBooksByAuthorsList().add(booksByAuthors);
                bookTitleId = em.merge(bookTitleId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BooksByAuthors booksByAuthors) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BooksByAuthors persistentBooksByAuthors = em.find(BooksByAuthors.class, booksByAuthors.getId());
            Authors authorIdOld = persistentBooksByAuthors.getAuthorId();
            Authors authorIdNew = booksByAuthors.getAuthorId();
            BookTitles bookTitleIdOld = persistentBooksByAuthors.getBookTitleId();
            BookTitles bookTitleIdNew = booksByAuthors.getBookTitleId();
            if (authorIdNew != null) {
                authorIdNew = em.getReference(authorIdNew.getClass(), authorIdNew.getId());
                booksByAuthors.setAuthorId(authorIdNew);
            }
            if (bookTitleIdNew != null) {
                bookTitleIdNew = em.getReference(bookTitleIdNew.getClass(), bookTitleIdNew.getId());
                booksByAuthors.setBookTitleId(bookTitleIdNew);
            }
            booksByAuthors = em.merge(booksByAuthors);
            if (authorIdOld != null && !authorIdOld.equals(authorIdNew)) {
                authorIdOld.getBooksByAuthorsList().remove(booksByAuthors);
                authorIdOld = em.merge(authorIdOld);
            }
            if (authorIdNew != null && !authorIdNew.equals(authorIdOld)) {
                authorIdNew.getBooksByAuthorsList().add(booksByAuthors);
                authorIdNew = em.merge(authorIdNew);
            }
            if (bookTitleIdOld != null && !bookTitleIdOld.equals(bookTitleIdNew)) {
                bookTitleIdOld.getBooksByAuthorsList().remove(booksByAuthors);
                bookTitleIdOld = em.merge(bookTitleIdOld);
            }
            if (bookTitleIdNew != null && !bookTitleIdNew.equals(bookTitleIdOld)) {
                bookTitleIdNew.getBooksByAuthorsList().add(booksByAuthors);
                bookTitleIdNew = em.merge(bookTitleIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = booksByAuthors.getId();
                if (findBooksByAuthors(id) == null) {
                    throw new NonexistentEntityException("The booksByAuthors with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BooksByAuthors booksByAuthors;
            try {
                booksByAuthors = em.getReference(BooksByAuthors.class, id);
                booksByAuthors.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The booksByAuthors with id " + id + " no longer exists.", enfe);
            }
            Authors authorId = booksByAuthors.getAuthorId();
            if (authorId != null) {
                authorId.getBooksByAuthorsList().remove(booksByAuthors);
                authorId = em.merge(authorId);
            }
            BookTitles bookTitleId = booksByAuthors.getBookTitleId();
            if (bookTitleId != null) {
                bookTitleId.getBooksByAuthorsList().remove(booksByAuthors);
                bookTitleId = em.merge(bookTitleId);
            }
            em.remove(booksByAuthors);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BooksByAuthors> findBooksByAuthorsEntities() {
        return findBooksByAuthorsEntities(true, -1, -1);
    }

    public List<BooksByAuthors> findBooksByAuthorsEntities(int maxResults, int firstResult) {
        return findBooksByAuthorsEntities(false, maxResults, firstResult);
    }

    private List<BooksByAuthors> findBooksByAuthorsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BooksByAuthors.class));
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

    public BooksByAuthors findBooksByAuthors(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BooksByAuthors.class, id);
        } finally {
            em.close();
        }
    }

    public int getBooksByAuthorsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BooksByAuthors> rt = cq.from(BooksByAuthors.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
