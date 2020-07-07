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
import Model.BookRequests;
import java.util.ArrayList;
import java.util.Collection;
import Model.Borrows;
import Model.Readers;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class ReadersJpaController implements Serializable
{

    public ReadersJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Readers readers)
    {
        if (readers.getBookRequestsCollection() == null)
        {
            readers.setBookRequestsCollection(new ArrayList<BookRequests>());
        }
        if (readers.getBorrowsCollection() == null)
        {
            readers.setBorrowsCollection(new ArrayList<Borrows>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Countries countryId = readers.getCountryId();
            if (countryId != null)
            {
                countryId = em.getReference(countryId.getClass(), countryId.getId());
                readers.setCountryId(countryId);
            }
            Collection<BookRequests> attachedBookRequestsCollection = new ArrayList<BookRequests>();
            for (BookRequests bookRequestsCollectionBookRequestsToAttach : readers.getBookRequestsCollection())
            {
                bookRequestsCollectionBookRequestsToAttach = em.getReference(bookRequestsCollectionBookRequestsToAttach.getClass(), bookRequestsCollectionBookRequestsToAttach.getId());
                attachedBookRequestsCollection.add(bookRequestsCollectionBookRequestsToAttach);
            }
            readers.setBookRequestsCollection(attachedBookRequestsCollection);
            Collection<Borrows> attachedBorrowsCollection = new ArrayList<Borrows>();
            for (Borrows borrowsCollectionBorrowsToAttach : readers.getBorrowsCollection())
            {
                borrowsCollectionBorrowsToAttach = em.getReference(borrowsCollectionBorrowsToAttach.getClass(), borrowsCollectionBorrowsToAttach.getId());
                attachedBorrowsCollection.add(borrowsCollectionBorrowsToAttach);
            }
            readers.setBorrowsCollection(attachedBorrowsCollection);
            em.persist(readers);
            if (countryId != null)
            {
                countryId.getReadersCollection().add(readers);
                countryId = em.merge(countryId);
            }
            for (BookRequests bookRequestsCollectionBookRequests : readers.getBookRequestsCollection())
            {
                Readers oldReaderIdOfBookRequestsCollectionBookRequests = bookRequestsCollectionBookRequests.getReaderId();
                bookRequestsCollectionBookRequests.setReaderId(readers);
                bookRequestsCollectionBookRequests = em.merge(bookRequestsCollectionBookRequests);
                if (oldReaderIdOfBookRequestsCollectionBookRequests != null)
                {
                    oldReaderIdOfBookRequestsCollectionBookRequests.getBookRequestsCollection().remove(bookRequestsCollectionBookRequests);
                    oldReaderIdOfBookRequestsCollectionBookRequests = em.merge(oldReaderIdOfBookRequestsCollectionBookRequests);
                }
            }
            for (Borrows borrowsCollectionBorrows : readers.getBorrowsCollection())
            {
                Readers oldReaderIdOfBorrowsCollectionBorrows = borrowsCollectionBorrows.getReaderId();
                borrowsCollectionBorrows.setReaderId(readers);
                borrowsCollectionBorrows = em.merge(borrowsCollectionBorrows);
                if (oldReaderIdOfBorrowsCollectionBorrows != null)
                {
                    oldReaderIdOfBorrowsCollectionBorrows.getBorrowsCollection().remove(borrowsCollectionBorrows);
                    oldReaderIdOfBorrowsCollectionBorrows = em.merge(oldReaderIdOfBorrowsCollectionBorrows);
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

    public void edit(Readers readers) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Readers persistentReaders = em.find(Readers.class, readers.getId());
            Countries countryIdOld = persistentReaders.getCountryId();
            Countries countryIdNew = readers.getCountryId();
            Collection<BookRequests> bookRequestsCollectionOld = persistentReaders.getBookRequestsCollection();
            Collection<BookRequests> bookRequestsCollectionNew = readers.getBookRequestsCollection();
            Collection<Borrows> borrowsCollectionOld = persistentReaders.getBorrowsCollection();
            Collection<Borrows> borrowsCollectionNew = readers.getBorrowsCollection();
            if (countryIdNew != null)
            {
                countryIdNew = em.getReference(countryIdNew.getClass(), countryIdNew.getId());
                readers.setCountryId(countryIdNew);
            }
            Collection<BookRequests> attachedBookRequestsCollectionNew = new ArrayList<BookRequests>();
            for (BookRequests bookRequestsCollectionNewBookRequestsToAttach : bookRequestsCollectionNew)
            {
                bookRequestsCollectionNewBookRequestsToAttach = em.getReference(bookRequestsCollectionNewBookRequestsToAttach.getClass(), bookRequestsCollectionNewBookRequestsToAttach.getId());
                attachedBookRequestsCollectionNew.add(bookRequestsCollectionNewBookRequestsToAttach);
            }
            bookRequestsCollectionNew = attachedBookRequestsCollectionNew;
            readers.setBookRequestsCollection(bookRequestsCollectionNew);
            Collection<Borrows> attachedBorrowsCollectionNew = new ArrayList<Borrows>();
            for (Borrows borrowsCollectionNewBorrowsToAttach : borrowsCollectionNew)
            {
                borrowsCollectionNewBorrowsToAttach = em.getReference(borrowsCollectionNewBorrowsToAttach.getClass(), borrowsCollectionNewBorrowsToAttach.getId());
                attachedBorrowsCollectionNew.add(borrowsCollectionNewBorrowsToAttach);
            }
            borrowsCollectionNew = attachedBorrowsCollectionNew;
            readers.setBorrowsCollection(borrowsCollectionNew);
            readers = em.merge(readers);
            if (countryIdOld != null && !countryIdOld.equals(countryIdNew))
            {
                countryIdOld.getReadersCollection().remove(readers);
                countryIdOld = em.merge(countryIdOld);
            }
            if (countryIdNew != null && !countryIdNew.equals(countryIdOld))
            {
                countryIdNew.getReadersCollection().add(readers);
                countryIdNew = em.merge(countryIdNew);
            }
            for (BookRequests bookRequestsCollectionOldBookRequests : bookRequestsCollectionOld)
            {
                if (!bookRequestsCollectionNew.contains(bookRequestsCollectionOldBookRequests))
                {
                    bookRequestsCollectionOldBookRequests.setReaderId(null);
                    bookRequestsCollectionOldBookRequests = em.merge(bookRequestsCollectionOldBookRequests);
                }
            }
            for (BookRequests bookRequestsCollectionNewBookRequests : bookRequestsCollectionNew)
            {
                if (!bookRequestsCollectionOld.contains(bookRequestsCollectionNewBookRequests))
                {
                    Readers oldReaderIdOfBookRequestsCollectionNewBookRequests = bookRequestsCollectionNewBookRequests.getReaderId();
                    bookRequestsCollectionNewBookRequests.setReaderId(readers);
                    bookRequestsCollectionNewBookRequests = em.merge(bookRequestsCollectionNewBookRequests);
                    if (oldReaderIdOfBookRequestsCollectionNewBookRequests != null && !oldReaderIdOfBookRequestsCollectionNewBookRequests.equals(readers))
                    {
                        oldReaderIdOfBookRequestsCollectionNewBookRequests.getBookRequestsCollection().remove(bookRequestsCollectionNewBookRequests);
                        oldReaderIdOfBookRequestsCollectionNewBookRequests = em.merge(oldReaderIdOfBookRequestsCollectionNewBookRequests);
                    }
                }
            }
            for (Borrows borrowsCollectionOldBorrows : borrowsCollectionOld)
            {
                if (!borrowsCollectionNew.contains(borrowsCollectionOldBorrows))
                {
                    borrowsCollectionOldBorrows.setReaderId(null);
                    borrowsCollectionOldBorrows = em.merge(borrowsCollectionOldBorrows);
                }
            }
            for (Borrows borrowsCollectionNewBorrows : borrowsCollectionNew)
            {
                if (!borrowsCollectionOld.contains(borrowsCollectionNewBorrows))
                {
                    Readers oldReaderIdOfBorrowsCollectionNewBorrows = borrowsCollectionNewBorrows.getReaderId();
                    borrowsCollectionNewBorrows.setReaderId(readers);
                    borrowsCollectionNewBorrows = em.merge(borrowsCollectionNewBorrows);
                    if (oldReaderIdOfBorrowsCollectionNewBorrows != null && !oldReaderIdOfBorrowsCollectionNewBorrows.equals(readers))
                    {
                        oldReaderIdOfBorrowsCollectionNewBorrows.getBorrowsCollection().remove(borrowsCollectionNewBorrows);
                        oldReaderIdOfBorrowsCollectionNewBorrows = em.merge(oldReaderIdOfBorrowsCollectionNewBorrows);
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
                Integer id = readers.getId();
                if (findReaders(id) == null)
                {
                    throw new NonexistentEntityException("The readers with id " + id + " no longer exists.");
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
            Readers readers;
            try
            {
                readers = em.getReference(Readers.class, id);
                readers.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The readers with id " + id + " no longer exists.", enfe);
            }
            Countries countryId = readers.getCountryId();
            if (countryId != null)
            {
                countryId.getReadersCollection().remove(readers);
                countryId = em.merge(countryId);
            }
            Collection<BookRequests> bookRequestsCollection = readers.getBookRequestsCollection();
            for (BookRequests bookRequestsCollectionBookRequests : bookRequestsCollection)
            {
                bookRequestsCollectionBookRequests.setReaderId(null);
                bookRequestsCollectionBookRequests = em.merge(bookRequestsCollectionBookRequests);
            }
            Collection<Borrows> borrowsCollection = readers.getBorrowsCollection();
            for (Borrows borrowsCollectionBorrows : borrowsCollection)
            {
                borrowsCollectionBorrows.setReaderId(null);
                borrowsCollectionBorrows = em.merge(borrowsCollectionBorrows);
            }
            em.remove(readers);
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

    public List<Readers> findReadersEntities()
    {
        return findReadersEntities(true, -1, -1);
    }

    public List<Readers> findReadersEntities(int maxResults, int firstResult)
    {
        return findReadersEntities(false, maxResults, firstResult);
    }

    private List<Readers> findReadersEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Readers.class));
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

    public Readers findReaders(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Readers.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getReadersCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Readers> rt = cq.from(Readers.class);
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
