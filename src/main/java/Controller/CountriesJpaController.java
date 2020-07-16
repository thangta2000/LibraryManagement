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
import Model.Staffs;
import java.util.ArrayList;
import java.util.Collection;
import Model.Authors;
import Model.BookTitles;
import Model.Readers;
import Model.Publishers;
import Model.BookRequests;
import Model.Countries;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class CountriesJpaController implements Serializable
{

    public CountriesJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Countries countries)
    {
        if (countries.getStaffsCollection() == null)
        {
            countries.setStaffsCollection(new ArrayList<Staffs>());
        }
        if (countries.getAuthorsCollection() == null)
        {
            countries.setAuthorsCollection(new ArrayList<Authors>());
        }
        if (countries.getBookTitlesCollection() == null)
        {
            countries.setBookTitlesCollection(new ArrayList<BookTitles>());
        }
        if (countries.getReadersCollection() == null)
        {
            countries.setReadersCollection(new ArrayList<Readers>());
        }
        if (countries.getPublishersCollection() == null)
        {
            countries.setPublishersCollection(new ArrayList<Publishers>());
        }
        if (countries.getBookRequestsCollection() == null)
        {
            countries.setBookRequestsCollection(new ArrayList<BookRequests>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Staffs> attachedStaffsCollection = new ArrayList<Staffs>();
            for (Staffs staffsCollectionStaffsToAttach : countries.getStaffsCollection())
            {
                staffsCollectionStaffsToAttach = em.getReference(staffsCollectionStaffsToAttach.getClass(), staffsCollectionStaffsToAttach.getId());
                attachedStaffsCollection.add(staffsCollectionStaffsToAttach);
            }
            countries.setStaffsCollection(attachedStaffsCollection);
            Collection<Authors> attachedAuthorsCollection = new ArrayList<Authors>();
            for (Authors authorsCollectionAuthorsToAttach : countries.getAuthorsCollection())
            {
                authorsCollectionAuthorsToAttach = em.getReference(authorsCollectionAuthorsToAttach.getClass(), authorsCollectionAuthorsToAttach.getId());
                attachedAuthorsCollection.add(authorsCollectionAuthorsToAttach);
            }
            countries.setAuthorsCollection(attachedAuthorsCollection);
            Collection<BookTitles> attachedBookTitlesCollection = new ArrayList<BookTitles>();
            for (BookTitles bookTitlesCollectionBookTitlesToAttach : countries.getBookTitlesCollection())
            {
                bookTitlesCollectionBookTitlesToAttach = em.getReference(bookTitlesCollectionBookTitlesToAttach.getClass(), bookTitlesCollectionBookTitlesToAttach.getId());
                attachedBookTitlesCollection.add(bookTitlesCollectionBookTitlesToAttach);
            }
            countries.setBookTitlesCollection(attachedBookTitlesCollection);
            Collection<Readers> attachedReadersCollection = new ArrayList<Readers>();
            for (Readers readersCollectionReadersToAttach : countries.getReadersCollection())
            {
                readersCollectionReadersToAttach = em.getReference(readersCollectionReadersToAttach.getClass(), readersCollectionReadersToAttach.getId());
                attachedReadersCollection.add(readersCollectionReadersToAttach);
            }
            countries.setReadersCollection(attachedReadersCollection);
            Collection<Publishers> attachedPublishersCollection = new ArrayList<Publishers>();
            for (Publishers publishersCollectionPublishersToAttach : countries.getPublishersCollection())
            {
                publishersCollectionPublishersToAttach = em.getReference(publishersCollectionPublishersToAttach.getClass(), publishersCollectionPublishersToAttach.getId());
                attachedPublishersCollection.add(publishersCollectionPublishersToAttach);
            }
            countries.setPublishersCollection(attachedPublishersCollection);
            Collection<BookRequests> attachedBookRequestsCollection = new ArrayList<BookRequests>();
            for (BookRequests bookRequestsCollectionBookRequestsToAttach : countries.getBookRequestsCollection())
            {
                bookRequestsCollectionBookRequestsToAttach = em.getReference(bookRequestsCollectionBookRequestsToAttach.getClass(), bookRequestsCollectionBookRequestsToAttach.getId());
                attachedBookRequestsCollection.add(bookRequestsCollectionBookRequestsToAttach);
            }
            countries.setBookRequestsCollection(attachedBookRequestsCollection);
            em.persist(countries);
            for (Staffs staffsCollectionStaffs : countries.getStaffsCollection())
            {
                Countries oldCountryIdOfStaffsCollectionStaffs = staffsCollectionStaffs.getCountryId();
                staffsCollectionStaffs.setCountryId(countries);
                staffsCollectionStaffs = em.merge(staffsCollectionStaffs);
                if (oldCountryIdOfStaffsCollectionStaffs != null)
                {
                    oldCountryIdOfStaffsCollectionStaffs.getStaffsCollection().remove(staffsCollectionStaffs);
                    oldCountryIdOfStaffsCollectionStaffs = em.merge(oldCountryIdOfStaffsCollectionStaffs);
                }
            }
            for (Authors authorsCollectionAuthors : countries.getAuthorsCollection())
            {
                Countries oldCountryIdOfAuthorsCollectionAuthors = authorsCollectionAuthors.getCountryId();
                authorsCollectionAuthors.setCountryId(countries);
                authorsCollectionAuthors = em.merge(authorsCollectionAuthors);
                if (oldCountryIdOfAuthorsCollectionAuthors != null)
                {
                    oldCountryIdOfAuthorsCollectionAuthors.getAuthorsCollection().remove(authorsCollectionAuthors);
                    oldCountryIdOfAuthorsCollectionAuthors = em.merge(oldCountryIdOfAuthorsCollectionAuthors);
                }
            }
            for (BookTitles bookTitlesCollectionBookTitles : countries.getBookTitlesCollection())
            {
                Countries oldCountryIdOfBookTitlesCollectionBookTitles = bookTitlesCollectionBookTitles.getCountryId();
                bookTitlesCollectionBookTitles.setCountryId(countries);
                bookTitlesCollectionBookTitles = em.merge(bookTitlesCollectionBookTitles);
                if (oldCountryIdOfBookTitlesCollectionBookTitles != null)
                {
                    oldCountryIdOfBookTitlesCollectionBookTitles.getBookTitlesCollection().remove(bookTitlesCollectionBookTitles);
                    oldCountryIdOfBookTitlesCollectionBookTitles = em.merge(oldCountryIdOfBookTitlesCollectionBookTitles);
                }
            }
            for (Readers readersCollectionReaders : countries.getReadersCollection())
            {
                Countries oldCountryIdOfReadersCollectionReaders = readersCollectionReaders.getCountryId();
                readersCollectionReaders.setCountryId(countries);
                readersCollectionReaders = em.merge(readersCollectionReaders);
                if (oldCountryIdOfReadersCollectionReaders != null)
                {
                    oldCountryIdOfReadersCollectionReaders.getReadersCollection().remove(readersCollectionReaders);
                    oldCountryIdOfReadersCollectionReaders = em.merge(oldCountryIdOfReadersCollectionReaders);
                }
            }
            for (Publishers publishersCollectionPublishers : countries.getPublishersCollection())
            {
                Countries oldCountryIdOfPublishersCollectionPublishers = publishersCollectionPublishers.getCountryId();
                publishersCollectionPublishers.setCountryId(countries);
                publishersCollectionPublishers = em.merge(publishersCollectionPublishers);
                if (oldCountryIdOfPublishersCollectionPublishers != null)
                {
                    oldCountryIdOfPublishersCollectionPublishers.getPublishersCollection().remove(publishersCollectionPublishers);
                    oldCountryIdOfPublishersCollectionPublishers = em.merge(oldCountryIdOfPublishersCollectionPublishers);
                }
            }
            for (BookRequests bookRequestsCollectionBookRequests : countries.getBookRequestsCollection())
            {
                Countries oldCountryIdOfBookRequestsCollectionBookRequests = bookRequestsCollectionBookRequests.getCountryId();
                bookRequestsCollectionBookRequests.setCountryId(countries);
                bookRequestsCollectionBookRequests = em.merge(bookRequestsCollectionBookRequests);
                if (oldCountryIdOfBookRequestsCollectionBookRequests != null)
                {
                    oldCountryIdOfBookRequestsCollectionBookRequests.getBookRequestsCollection().remove(bookRequestsCollectionBookRequests);
                    oldCountryIdOfBookRequestsCollectionBookRequests = em.merge(oldCountryIdOfBookRequestsCollectionBookRequests);
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

    public void edit(Countries countries) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Countries persistentCountries = em.find(Countries.class, countries.getId());
            Collection<Staffs> staffsCollectionOld = persistentCountries.getStaffsCollection();
            Collection<Staffs> staffsCollectionNew = countries.getStaffsCollection();
            Collection<Authors> authorsCollectionOld = persistentCountries.getAuthorsCollection();
            Collection<Authors> authorsCollectionNew = countries.getAuthorsCollection();
            Collection<BookTitles> bookTitlesCollectionOld = persistentCountries.getBookTitlesCollection();
            Collection<BookTitles> bookTitlesCollectionNew = countries.getBookTitlesCollection();
            Collection<Readers> readersCollectionOld = persistentCountries.getReadersCollection();
            Collection<Readers> readersCollectionNew = countries.getReadersCollection();
            Collection<Publishers> publishersCollectionOld = persistentCountries.getPublishersCollection();
            Collection<Publishers> publishersCollectionNew = countries.getPublishersCollection();
            Collection<BookRequests> bookRequestsCollectionOld = persistentCountries.getBookRequestsCollection();
            Collection<BookRequests> bookRequestsCollectionNew = countries.getBookRequestsCollection();
            Collection<Staffs> attachedStaffsCollectionNew = new ArrayList<Staffs>();
            for (Staffs staffsCollectionNewStaffsToAttach : staffsCollectionNew)
            {
                staffsCollectionNewStaffsToAttach = em.getReference(staffsCollectionNewStaffsToAttach.getClass(), staffsCollectionNewStaffsToAttach.getId());
                attachedStaffsCollectionNew.add(staffsCollectionNewStaffsToAttach);
            }
            staffsCollectionNew = attachedStaffsCollectionNew;
            countries.setStaffsCollection(staffsCollectionNew);
            Collection<Authors> attachedAuthorsCollectionNew = new ArrayList<Authors>();
            for (Authors authorsCollectionNewAuthorsToAttach : authorsCollectionNew)
            {
                authorsCollectionNewAuthorsToAttach = em.getReference(authorsCollectionNewAuthorsToAttach.getClass(), authorsCollectionNewAuthorsToAttach.getId());
                attachedAuthorsCollectionNew.add(authorsCollectionNewAuthorsToAttach);
            }
            authorsCollectionNew = attachedAuthorsCollectionNew;
            countries.setAuthorsCollection(authorsCollectionNew);
            Collection<BookTitles> attachedBookTitlesCollectionNew = new ArrayList<BookTitles>();
            for (BookTitles bookTitlesCollectionNewBookTitlesToAttach : bookTitlesCollectionNew)
            {
                bookTitlesCollectionNewBookTitlesToAttach = em.getReference(bookTitlesCollectionNewBookTitlesToAttach.getClass(), bookTitlesCollectionNewBookTitlesToAttach.getId());
                attachedBookTitlesCollectionNew.add(bookTitlesCollectionNewBookTitlesToAttach);
            }
            bookTitlesCollectionNew = attachedBookTitlesCollectionNew;
            countries.setBookTitlesCollection(bookTitlesCollectionNew);
            Collection<Readers> attachedReadersCollectionNew = new ArrayList<Readers>();
            for (Readers readersCollectionNewReadersToAttach : readersCollectionNew)
            {
                readersCollectionNewReadersToAttach = em.getReference(readersCollectionNewReadersToAttach.getClass(), readersCollectionNewReadersToAttach.getId());
                attachedReadersCollectionNew.add(readersCollectionNewReadersToAttach);
            }
            readersCollectionNew = attachedReadersCollectionNew;
            countries.setReadersCollection(readersCollectionNew);
            Collection<Publishers> attachedPublishersCollectionNew = new ArrayList<Publishers>();
            for (Publishers publishersCollectionNewPublishersToAttach : publishersCollectionNew)
            {
                publishersCollectionNewPublishersToAttach = em.getReference(publishersCollectionNewPublishersToAttach.getClass(), publishersCollectionNewPublishersToAttach.getId());
                attachedPublishersCollectionNew.add(publishersCollectionNewPublishersToAttach);
            }
            publishersCollectionNew = attachedPublishersCollectionNew;
            countries.setPublishersCollection(publishersCollectionNew);
            Collection<BookRequests> attachedBookRequestsCollectionNew = new ArrayList<BookRequests>();
            for (BookRequests bookRequestsCollectionNewBookRequestsToAttach : bookRequestsCollectionNew)
            {
                bookRequestsCollectionNewBookRequestsToAttach = em.getReference(bookRequestsCollectionNewBookRequestsToAttach.getClass(), bookRequestsCollectionNewBookRequestsToAttach.getId());
                attachedBookRequestsCollectionNew.add(bookRequestsCollectionNewBookRequestsToAttach);
            }
            bookRequestsCollectionNew = attachedBookRequestsCollectionNew;
            countries.setBookRequestsCollection(bookRequestsCollectionNew);
            countries = em.merge(countries);
            for (Staffs staffsCollectionOldStaffs : staffsCollectionOld)
            {
                if (!staffsCollectionNew.contains(staffsCollectionOldStaffs))
                {
                    staffsCollectionOldStaffs.setCountryId(null);
                    staffsCollectionOldStaffs = em.merge(staffsCollectionOldStaffs);
                }
            }
            for (Staffs staffsCollectionNewStaffs : staffsCollectionNew)
            {
                if (!staffsCollectionOld.contains(staffsCollectionNewStaffs))
                {
                    Countries oldCountryIdOfStaffsCollectionNewStaffs = staffsCollectionNewStaffs.getCountryId();
                    staffsCollectionNewStaffs.setCountryId(countries);
                    staffsCollectionNewStaffs = em.merge(staffsCollectionNewStaffs);
                    if (oldCountryIdOfStaffsCollectionNewStaffs != null && !oldCountryIdOfStaffsCollectionNewStaffs.equals(countries))
                    {
                        oldCountryIdOfStaffsCollectionNewStaffs.getStaffsCollection().remove(staffsCollectionNewStaffs);
                        oldCountryIdOfStaffsCollectionNewStaffs = em.merge(oldCountryIdOfStaffsCollectionNewStaffs);
                    }
                }
            }
            for (Authors authorsCollectionOldAuthors : authorsCollectionOld)
            {
                if (!authorsCollectionNew.contains(authorsCollectionOldAuthors))
                {
                    authorsCollectionOldAuthors.setCountryId(null);
                    authorsCollectionOldAuthors = em.merge(authorsCollectionOldAuthors);
                }
            }
            for (Authors authorsCollectionNewAuthors : authorsCollectionNew)
            {
                if (!authorsCollectionOld.contains(authorsCollectionNewAuthors))
                {
                    Countries oldCountryIdOfAuthorsCollectionNewAuthors = authorsCollectionNewAuthors.getCountryId();
                    authorsCollectionNewAuthors.setCountryId(countries);
                    authorsCollectionNewAuthors = em.merge(authorsCollectionNewAuthors);
                    if (oldCountryIdOfAuthorsCollectionNewAuthors != null && !oldCountryIdOfAuthorsCollectionNewAuthors.equals(countries))
                    {
                        oldCountryIdOfAuthorsCollectionNewAuthors.getAuthorsCollection().remove(authorsCollectionNewAuthors);
                        oldCountryIdOfAuthorsCollectionNewAuthors = em.merge(oldCountryIdOfAuthorsCollectionNewAuthors);
                    }
                }
            }
            for (BookTitles bookTitlesCollectionOldBookTitles : bookTitlesCollectionOld)
            {
                if (!bookTitlesCollectionNew.contains(bookTitlesCollectionOldBookTitles))
                {
                    bookTitlesCollectionOldBookTitles.setCountryId(null);
                    bookTitlesCollectionOldBookTitles = em.merge(bookTitlesCollectionOldBookTitles);
                }
            }
            for (BookTitles bookTitlesCollectionNewBookTitles : bookTitlesCollectionNew)
            {
                if (!bookTitlesCollectionOld.contains(bookTitlesCollectionNewBookTitles))
                {
                    Countries oldCountryIdOfBookTitlesCollectionNewBookTitles = bookTitlesCollectionNewBookTitles.getCountryId();
                    bookTitlesCollectionNewBookTitles.setCountryId(countries);
                    bookTitlesCollectionNewBookTitles = em.merge(bookTitlesCollectionNewBookTitles);
                    if (oldCountryIdOfBookTitlesCollectionNewBookTitles != null && !oldCountryIdOfBookTitlesCollectionNewBookTitles.equals(countries))
                    {
                        oldCountryIdOfBookTitlesCollectionNewBookTitles.getBookTitlesCollection().remove(bookTitlesCollectionNewBookTitles);
                        oldCountryIdOfBookTitlesCollectionNewBookTitles = em.merge(oldCountryIdOfBookTitlesCollectionNewBookTitles);
                    }
                }
            }
            for (Readers readersCollectionOldReaders : readersCollectionOld)
            {
                if (!readersCollectionNew.contains(readersCollectionOldReaders))
                {
                    readersCollectionOldReaders.setCountryId(null);
                    readersCollectionOldReaders = em.merge(readersCollectionOldReaders);
                }
            }
            for (Readers readersCollectionNewReaders : readersCollectionNew)
            {
                if (!readersCollectionOld.contains(readersCollectionNewReaders))
                {
                    Countries oldCountryIdOfReadersCollectionNewReaders = readersCollectionNewReaders.getCountryId();
                    readersCollectionNewReaders.setCountryId(countries);
                    readersCollectionNewReaders = em.merge(readersCollectionNewReaders);
                    if (oldCountryIdOfReadersCollectionNewReaders != null && !oldCountryIdOfReadersCollectionNewReaders.equals(countries))
                    {
                        oldCountryIdOfReadersCollectionNewReaders.getReadersCollection().remove(readersCollectionNewReaders);
                        oldCountryIdOfReadersCollectionNewReaders = em.merge(oldCountryIdOfReadersCollectionNewReaders);
                    }
                }
            }
            for (Publishers publishersCollectionOldPublishers : publishersCollectionOld)
            {
                if (!publishersCollectionNew.contains(publishersCollectionOldPublishers))
                {
                    publishersCollectionOldPublishers.setCountryId(null);
                    publishersCollectionOldPublishers = em.merge(publishersCollectionOldPublishers);
                }
            }
            for (Publishers publishersCollectionNewPublishers : publishersCollectionNew)
            {
                if (!publishersCollectionOld.contains(publishersCollectionNewPublishers))
                {
                    Countries oldCountryIdOfPublishersCollectionNewPublishers = publishersCollectionNewPublishers.getCountryId();
                    publishersCollectionNewPublishers.setCountryId(countries);
                    publishersCollectionNewPublishers = em.merge(publishersCollectionNewPublishers);
                    if (oldCountryIdOfPublishersCollectionNewPublishers != null && !oldCountryIdOfPublishersCollectionNewPublishers.equals(countries))
                    {
                        oldCountryIdOfPublishersCollectionNewPublishers.getPublishersCollection().remove(publishersCollectionNewPublishers);
                        oldCountryIdOfPublishersCollectionNewPublishers = em.merge(oldCountryIdOfPublishersCollectionNewPublishers);
                    }
                }
            }
            for (BookRequests bookRequestsCollectionOldBookRequests : bookRequestsCollectionOld)
            {
                if (!bookRequestsCollectionNew.contains(bookRequestsCollectionOldBookRequests))
                {
                    bookRequestsCollectionOldBookRequests.setCountryId(null);
                    bookRequestsCollectionOldBookRequests = em.merge(bookRequestsCollectionOldBookRequests);
                }
            }
            for (BookRequests bookRequestsCollectionNewBookRequests : bookRequestsCollectionNew)
            {
                if (!bookRequestsCollectionOld.contains(bookRequestsCollectionNewBookRequests))
                {
                    Countries oldCountryIdOfBookRequestsCollectionNewBookRequests = bookRequestsCollectionNewBookRequests.getCountryId();
                    bookRequestsCollectionNewBookRequests.setCountryId(countries);
                    bookRequestsCollectionNewBookRequests = em.merge(bookRequestsCollectionNewBookRequests);
                    if (oldCountryIdOfBookRequestsCollectionNewBookRequests != null && !oldCountryIdOfBookRequestsCollectionNewBookRequests.equals(countries))
                    {
                        oldCountryIdOfBookRequestsCollectionNewBookRequests.getBookRequestsCollection().remove(bookRequestsCollectionNewBookRequests);
                        oldCountryIdOfBookRequestsCollectionNewBookRequests = em.merge(oldCountryIdOfBookRequestsCollectionNewBookRequests);
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
                Integer id = countries.getId();
                if (findCountries(id) == null)
                {
                    throw new NonexistentEntityException("The countries with id " + id + " no longer exists.");
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
            Countries countries;
            try
            {
                countries = em.getReference(Countries.class, id);
                countries.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The countries with id " + id + " no longer exists.", enfe);
            }
            Collection<Staffs> staffsCollection = countries.getStaffsCollection();
            for (Staffs staffsCollectionStaffs : staffsCollection)
            {
                staffsCollectionStaffs.setCountryId(null);
                staffsCollectionStaffs = em.merge(staffsCollectionStaffs);
            }
            Collection<Authors> authorsCollection = countries.getAuthorsCollection();
            for (Authors authorsCollectionAuthors : authorsCollection)
            {
                authorsCollectionAuthors.setCountryId(null);
                authorsCollectionAuthors = em.merge(authorsCollectionAuthors);
            }
            Collection<BookTitles> bookTitlesCollection = countries.getBookTitlesCollection();
            for (BookTitles bookTitlesCollectionBookTitles : bookTitlesCollection)
            {
                bookTitlesCollectionBookTitles.setCountryId(null);
                bookTitlesCollectionBookTitles = em.merge(bookTitlesCollectionBookTitles);
            }
            Collection<Readers> readersCollection = countries.getReadersCollection();
            for (Readers readersCollectionReaders : readersCollection)
            {
                readersCollectionReaders.setCountryId(null);
                readersCollectionReaders = em.merge(readersCollectionReaders);
            }
            Collection<Publishers> publishersCollection = countries.getPublishersCollection();
            for (Publishers publishersCollectionPublishers : publishersCollection)
            {
                publishersCollectionPublishers.setCountryId(null);
                publishersCollectionPublishers = em.merge(publishersCollectionPublishers);
            }
            Collection<BookRequests> bookRequestsCollection = countries.getBookRequestsCollection();
            for (BookRequests bookRequestsCollectionBookRequests : bookRequestsCollection)
            {
                bookRequestsCollectionBookRequests.setCountryId(null);
                bookRequestsCollectionBookRequests = em.merge(bookRequestsCollectionBookRequests);
            }
            em.remove(countries);
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

    public List<Countries> findCountriesEntities()
    {
        return findCountriesEntities(true, -1, -1);
    }

    public List<Countries> findCountriesEntities(int maxResults, int firstResult)
    {
        return findCountriesEntities(false, maxResults, firstResult);
    }

    private List<Countries> findCountriesEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Countries.class));
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

    public Countries findCountries(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Countries.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getCountriesCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Countries> rt = cq.from(Countries.class);
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
