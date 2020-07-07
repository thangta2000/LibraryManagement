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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tkang_85a
 */
public class AuthorsJpaController implements Serializable {

    public AuthorsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Authors authors) {
        if (authors.getBooksByAuthorsList() == null) {
            authors.setBooksByAuthorsList(new ArrayList<BooksByAuthors>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Countries countryId = authors.getCountryId();
            if (countryId != null) {
                countryId = em.getReference(countryId.getClass(), countryId.getId());
                authors.setCountryId(countryId);
            }
            List<BooksByAuthors> attachedBooksByAuthorsList = new ArrayList<BooksByAuthors>();
            for (BooksByAuthors booksByAuthorsListBooksByAuthorsToAttach : authors.getBooksByAuthorsList()) {
                booksByAuthorsListBooksByAuthorsToAttach = em.getReference(booksByAuthorsListBooksByAuthorsToAttach.getClass(), booksByAuthorsListBooksByAuthorsToAttach.getId());
                attachedBooksByAuthorsList.add(booksByAuthorsListBooksByAuthorsToAttach);
            }
            authors.setBooksByAuthorsList(attachedBooksByAuthorsList);
            em.persist(authors);
            if (countryId != null) {
                countryId.getAuthorsList().add(authors);
                countryId = em.merge(countryId);
            }
            for (BooksByAuthors booksByAuthorsListBooksByAuthors : authors.getBooksByAuthorsList()) {
                Authors oldAuthorIdOfBooksByAuthorsListBooksByAuthors = booksByAuthorsListBooksByAuthors.getAuthorId();
                booksByAuthorsListBooksByAuthors.setAuthorId(authors);
                booksByAuthorsListBooksByAuthors = em.merge(booksByAuthorsListBooksByAuthors);
                if (oldAuthorIdOfBooksByAuthorsListBooksByAuthors != null) {
                    oldAuthorIdOfBooksByAuthorsListBooksByAuthors.getBooksByAuthorsList().remove(booksByAuthorsListBooksByAuthors);
                    oldAuthorIdOfBooksByAuthorsListBooksByAuthors = em.merge(oldAuthorIdOfBooksByAuthorsListBooksByAuthors);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Authors authors) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Authors persistentAuthors = em.find(Authors.class, authors.getId());
            Countries countryIdOld = persistentAuthors.getCountryId();
            Countries countryIdNew = authors.getCountryId();
            List<BooksByAuthors> booksByAuthorsListOld = persistentAuthors.getBooksByAuthorsList();
            List<BooksByAuthors> booksByAuthorsListNew = authors.getBooksByAuthorsList();
            if (countryIdNew != null) {
                countryIdNew = em.getReference(countryIdNew.getClass(), countryIdNew.getId());
                authors.setCountryId(countryIdNew);
            }
            List<BooksByAuthors> attachedBooksByAuthorsListNew = new ArrayList<BooksByAuthors>();
            for (BooksByAuthors booksByAuthorsListNewBooksByAuthorsToAttach : booksByAuthorsListNew) {
                booksByAuthorsListNewBooksByAuthorsToAttach = em.getReference(booksByAuthorsListNewBooksByAuthorsToAttach.getClass(), booksByAuthorsListNewBooksByAuthorsToAttach.getId());
                attachedBooksByAuthorsListNew.add(booksByAuthorsListNewBooksByAuthorsToAttach);
            }
            booksByAuthorsListNew = attachedBooksByAuthorsListNew;
            authors.setBooksByAuthorsList(booksByAuthorsListNew);
            authors = em.merge(authors);
            if (countryIdOld != null && !countryIdOld.equals(countryIdNew)) {
                countryIdOld.getAuthorsList().remove(authors);
                countryIdOld = em.merge(countryIdOld);
            }
            if (countryIdNew != null && !countryIdNew.equals(countryIdOld)) {
                countryIdNew.getAuthorsList().add(authors);
                countryIdNew = em.merge(countryIdNew);
            }
            for (BooksByAuthors booksByAuthorsListOldBooksByAuthors : booksByAuthorsListOld) {
                if (!booksByAuthorsListNew.contains(booksByAuthorsListOldBooksByAuthors)) {
                    booksByAuthorsListOldBooksByAuthors.setAuthorId(null);
                    booksByAuthorsListOldBooksByAuthors = em.merge(booksByAuthorsListOldBooksByAuthors);
                }
            }
            for (BooksByAuthors booksByAuthorsListNewBooksByAuthors : booksByAuthorsListNew) {
                if (!booksByAuthorsListOld.contains(booksByAuthorsListNewBooksByAuthors)) {
                    Authors oldAuthorIdOfBooksByAuthorsListNewBooksByAuthors = booksByAuthorsListNewBooksByAuthors.getAuthorId();
                    booksByAuthorsListNewBooksByAuthors.setAuthorId(authors);
                    booksByAuthorsListNewBooksByAuthors = em.merge(booksByAuthorsListNewBooksByAuthors);
                    if (oldAuthorIdOfBooksByAuthorsListNewBooksByAuthors != null && !oldAuthorIdOfBooksByAuthorsListNewBooksByAuthors.equals(authors)) {
                        oldAuthorIdOfBooksByAuthorsListNewBooksByAuthors.getBooksByAuthorsList().remove(booksByAuthorsListNewBooksByAuthors);
                        oldAuthorIdOfBooksByAuthorsListNewBooksByAuthors = em.merge(oldAuthorIdOfBooksByAuthorsListNewBooksByAuthors);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = authors.getId();
                if (findAuthors(id) == null) {
                    throw new NonexistentEntityException("The authors with id " + id + " no longer exists.");
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
            Authors authors;
            try {
                authors = em.getReference(Authors.class, id);
                authors.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The authors with id " + id + " no longer exists.", enfe);
            }
            Countries countryId = authors.getCountryId();
            if (countryId != null) {
                countryId.getAuthorsList().remove(authors);
                countryId = em.merge(countryId);
            }
            List<BooksByAuthors> booksByAuthorsList = authors.getBooksByAuthorsList();
            for (BooksByAuthors booksByAuthorsListBooksByAuthors : booksByAuthorsList) {
                booksByAuthorsListBooksByAuthors.setAuthorId(null);
                booksByAuthorsListBooksByAuthors = em.merge(booksByAuthorsListBooksByAuthors);
            }
            em.remove(authors);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Authors> findAuthorsEntities() {
        return findAuthorsEntities(true, -1, -1);
    }

    public List<Authors> findAuthorsEntities(int maxResults, int firstResult) {
        return findAuthorsEntities(false, maxResults, firstResult);
    }

    private List<Authors> findAuthorsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Authors.class));
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

    public Authors findAuthors(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Authors.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuthorsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Authors> rt = cq.from(Authors.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
