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
import java.util.List;
import Model.Authors;
import Model.BookTitles;
import Model.Readers;
import Model.Publishers;
import Model.BookRequests;
import Model.Countries;
import Utility.Factory;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author admin
 */
public class CountriesJpaController implements Serializable
{

//    public CountriesJpaController(EntityManagerFactory emf)
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

    public static void create(Countries countries)
    {
        if (countries.getStaffsList() == null)
        {
            countries.setStaffsList(new ArrayList<Staffs>());
        }
        if (countries.getAuthorsList() == null)
        {
            countries.setAuthorsList(new ArrayList<Authors>());
        }
        if (countries.getBookTitlesList() == null)
        {
            countries.setBookTitlesList(new ArrayList<BookTitles>());
        }
        if (countries.getReadersList() == null)
        {
            countries.setReadersList(new ArrayList<Readers>());
        }
        if (countries.getPublishersList() == null)
        {
            countries.setPublishersList(new ArrayList<Publishers>());
        }
        if (countries.getBookRequestsList() == null)
        {
            countries.setBookRequestsList(new ArrayList<BookRequests>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Staffs> attachedStaffsList = new ArrayList<Staffs>();
            for (Staffs staffsListStaffsToAttach : countries.getStaffsList())
            {
                staffsListStaffsToAttach = em.getReference(staffsListStaffsToAttach.getClass(), staffsListStaffsToAttach.getId());
                attachedStaffsList.add(staffsListStaffsToAttach);
            }
            countries.setStaffsList(attachedStaffsList);
            List<Authors> attachedAuthorsList = new ArrayList<Authors>();
            for (Authors authorsListAuthorsToAttach : countries.getAuthorsList())
            {
                authorsListAuthorsToAttach = em.getReference(authorsListAuthorsToAttach.getClass(), authorsListAuthorsToAttach.getId());
                attachedAuthorsList.add(authorsListAuthorsToAttach);
            }
            countries.setAuthorsList(attachedAuthorsList);
            List<BookTitles> attachedBookTitlesList = new ArrayList<BookTitles>();
            for (BookTitles bookTitlesListBookTitlesToAttach : countries.getBookTitlesList())
            {
                bookTitlesListBookTitlesToAttach = em.getReference(bookTitlesListBookTitlesToAttach.getClass(), bookTitlesListBookTitlesToAttach.getId());
                attachedBookTitlesList.add(bookTitlesListBookTitlesToAttach);
            }
            countries.setBookTitlesList(attachedBookTitlesList);
            List<Readers> attachedReadersList = new ArrayList<Readers>();
            for (Readers readersListReadersToAttach : countries.getReadersList())
            {
                readersListReadersToAttach = em.getReference(readersListReadersToAttach.getClass(), readersListReadersToAttach.getId());
                attachedReadersList.add(readersListReadersToAttach);
            }
            countries.setReadersList(attachedReadersList);
            List<Publishers> attachedPublishersList = new ArrayList<Publishers>();
            for (Publishers publishersListPublishersToAttach : countries.getPublishersList())
            {
                publishersListPublishersToAttach = em.getReference(publishersListPublishersToAttach.getClass(), publishersListPublishersToAttach.getId());
                attachedPublishersList.add(publishersListPublishersToAttach);
            }
            countries.setPublishersList(attachedPublishersList);
            List<BookRequests> attachedBookRequestsList = new ArrayList<BookRequests>();
            for (BookRequests bookRequestsListBookRequestsToAttach : countries.getBookRequestsList())
            {
                bookRequestsListBookRequestsToAttach = em.getReference(bookRequestsListBookRequestsToAttach.getClass(), bookRequestsListBookRequestsToAttach.getId());
                attachedBookRequestsList.add(bookRequestsListBookRequestsToAttach);
            }
            countries.setBookRequestsList(attachedBookRequestsList);
            em.persist(countries);
            for (Staffs staffsListStaffs : countries.getStaffsList())
            {
                Countries oldCountryIdOfStaffsListStaffs = staffsListStaffs.getCountryId();
                staffsListStaffs.setCountryId(countries);
                staffsListStaffs = em.merge(staffsListStaffs);
                if (oldCountryIdOfStaffsListStaffs != null)
                {
                    oldCountryIdOfStaffsListStaffs.getStaffsList().remove(staffsListStaffs);
                    oldCountryIdOfStaffsListStaffs = em.merge(oldCountryIdOfStaffsListStaffs);
                }
            }
            for (Authors authorsListAuthors : countries.getAuthorsList())
            {
                Countries oldCountryIdOfAuthorsListAuthors = authorsListAuthors.getCountryId();
                authorsListAuthors.setCountryId(countries);
                authorsListAuthors = em.merge(authorsListAuthors);
                if (oldCountryIdOfAuthorsListAuthors != null)
                {
                    oldCountryIdOfAuthorsListAuthors.getAuthorsList().remove(authorsListAuthors);
                    oldCountryIdOfAuthorsListAuthors = em.merge(oldCountryIdOfAuthorsListAuthors);
                }
            }
            for (BookTitles bookTitlesListBookTitles : countries.getBookTitlesList())
            {
                Countries oldCountryIdOfBookTitlesListBookTitles = bookTitlesListBookTitles.getCountryId();
                bookTitlesListBookTitles.setCountryId(countries);
                bookTitlesListBookTitles = em.merge(bookTitlesListBookTitles);
                if (oldCountryIdOfBookTitlesListBookTitles != null)
                {
                    oldCountryIdOfBookTitlesListBookTitles.getBookTitlesList().remove(bookTitlesListBookTitles);
                    oldCountryIdOfBookTitlesListBookTitles = em.merge(oldCountryIdOfBookTitlesListBookTitles);
                }
            }
            for (Readers readersListReaders : countries.getReadersList())
            {
                Countries oldCountryIdOfReadersListReaders = readersListReaders.getCountryId();
                readersListReaders.setCountryId(countries);
                readersListReaders = em.merge(readersListReaders);
                if (oldCountryIdOfReadersListReaders != null)
                {
                    oldCountryIdOfReadersListReaders.getReadersList().remove(readersListReaders);
                    oldCountryIdOfReadersListReaders = em.merge(oldCountryIdOfReadersListReaders);
                }
            }
            for (Publishers publishersListPublishers : countries.getPublishersList())
            {
                Countries oldCountryIdOfPublishersListPublishers = publishersListPublishers.getCountryId();
                publishersListPublishers.setCountryId(countries);
                publishersListPublishers = em.merge(publishersListPublishers);
                if (oldCountryIdOfPublishersListPublishers != null)
                {
                    oldCountryIdOfPublishersListPublishers.getPublishersList().remove(publishersListPublishers);
                    oldCountryIdOfPublishersListPublishers = em.merge(oldCountryIdOfPublishersListPublishers);
                }
            }
            for (BookRequests bookRequestsListBookRequests : countries.getBookRequestsList())
            {
                Countries oldCountryIdOfBookRequestsListBookRequests = bookRequestsListBookRequests.getCountryId();
                bookRequestsListBookRequests.setCountryId(countries);
                bookRequestsListBookRequests = em.merge(bookRequestsListBookRequests);
                if (oldCountryIdOfBookRequestsListBookRequests != null)
                {
                    oldCountryIdOfBookRequestsListBookRequests.getBookRequestsList().remove(bookRequestsListBookRequests);
                    oldCountryIdOfBookRequestsListBookRequests = em.merge(oldCountryIdOfBookRequestsListBookRequests);
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

    public static void edit(Countries countries) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Countries persistentCountries = em.find(Countries.class, countries.getId());
            List<Staffs> staffsListOld = persistentCountries.getStaffsList();
            List<Staffs> staffsListNew = countries.getStaffsList();
            List<Authors> authorsListOld = persistentCountries.getAuthorsList();
            List<Authors> authorsListNew = countries.getAuthorsList();
            List<BookTitles> bookTitlesListOld = persistentCountries.getBookTitlesList();
            List<BookTitles> bookTitlesListNew = countries.getBookTitlesList();
            List<Readers> readersListOld = persistentCountries.getReadersList();
            List<Readers> readersListNew = countries.getReadersList();
            List<Publishers> publishersListOld = persistentCountries.getPublishersList();
            List<Publishers> publishersListNew = countries.getPublishersList();
            List<BookRequests> bookRequestsListOld = persistentCountries.getBookRequestsList();
            List<BookRequests> bookRequestsListNew = countries.getBookRequestsList();
            List<Staffs> attachedStaffsListNew = new ArrayList<Staffs>();
            for (Staffs staffsListNewStaffsToAttach : staffsListNew)
            {
                staffsListNewStaffsToAttach = em.getReference(staffsListNewStaffsToAttach.getClass(), staffsListNewStaffsToAttach.getId());
                attachedStaffsListNew.add(staffsListNewStaffsToAttach);
            }
            staffsListNew = attachedStaffsListNew;
            countries.setStaffsList(staffsListNew);
            List<Authors> attachedAuthorsListNew = new ArrayList<Authors>();
            for (Authors authorsListNewAuthorsToAttach : authorsListNew)
            {
                authorsListNewAuthorsToAttach = em.getReference(authorsListNewAuthorsToAttach.getClass(), authorsListNewAuthorsToAttach.getId());
                attachedAuthorsListNew.add(authorsListNewAuthorsToAttach);
            }
            authorsListNew = attachedAuthorsListNew;
            countries.setAuthorsList(authorsListNew);
            List<BookTitles> attachedBookTitlesListNew = new ArrayList<BookTitles>();
            for (BookTitles bookTitlesListNewBookTitlesToAttach : bookTitlesListNew)
            {
                bookTitlesListNewBookTitlesToAttach = em.getReference(bookTitlesListNewBookTitlesToAttach.getClass(), bookTitlesListNewBookTitlesToAttach.getId());
                attachedBookTitlesListNew.add(bookTitlesListNewBookTitlesToAttach);
            }
            bookTitlesListNew = attachedBookTitlesListNew;
            countries.setBookTitlesList(bookTitlesListNew);
            List<Readers> attachedReadersListNew = new ArrayList<Readers>();
            for (Readers readersListNewReadersToAttach : readersListNew)
            {
                readersListNewReadersToAttach = em.getReference(readersListNewReadersToAttach.getClass(), readersListNewReadersToAttach.getId());
                attachedReadersListNew.add(readersListNewReadersToAttach);
            }
            readersListNew = attachedReadersListNew;
            countries.setReadersList(readersListNew);
            List<Publishers> attachedPublishersListNew = new ArrayList<Publishers>();
            for (Publishers publishersListNewPublishersToAttach : publishersListNew)
            {
                publishersListNewPublishersToAttach = em.getReference(publishersListNewPublishersToAttach.getClass(), publishersListNewPublishersToAttach.getId());
                attachedPublishersListNew.add(publishersListNewPublishersToAttach);
            }
            publishersListNew = attachedPublishersListNew;
            countries.setPublishersList(publishersListNew);
            List<BookRequests> attachedBookRequestsListNew = new ArrayList<BookRequests>();
            for (BookRequests bookRequestsListNewBookRequestsToAttach : bookRequestsListNew)
            {
                bookRequestsListNewBookRequestsToAttach = em.getReference(bookRequestsListNewBookRequestsToAttach.getClass(), bookRequestsListNewBookRequestsToAttach.getId());
                attachedBookRequestsListNew.add(bookRequestsListNewBookRequestsToAttach);
            }
            bookRequestsListNew = attachedBookRequestsListNew;
            countries.setBookRequestsList(bookRequestsListNew);
            countries = em.merge(countries);
            for (Staffs staffsListOldStaffs : staffsListOld)
            {
                if (!staffsListNew.contains(staffsListOldStaffs))
                {
                    staffsListOldStaffs.setCountryId(null);
                    staffsListOldStaffs = em.merge(staffsListOldStaffs);
                }
            }
            for (Staffs staffsListNewStaffs : staffsListNew)
            {
                if (!staffsListOld.contains(staffsListNewStaffs))
                {
                    Countries oldCountryIdOfStaffsListNewStaffs = staffsListNewStaffs.getCountryId();
                    staffsListNewStaffs.setCountryId(countries);
                    staffsListNewStaffs = em.merge(staffsListNewStaffs);
                    if (oldCountryIdOfStaffsListNewStaffs != null && !oldCountryIdOfStaffsListNewStaffs.equals(countries))
                    {
                        oldCountryIdOfStaffsListNewStaffs.getStaffsList().remove(staffsListNewStaffs);
                        oldCountryIdOfStaffsListNewStaffs = em.merge(oldCountryIdOfStaffsListNewStaffs);
                    }
                }
            }
            for (Authors authorsListOldAuthors : authorsListOld)
            {
                if (!authorsListNew.contains(authorsListOldAuthors))
                {
                    authorsListOldAuthors.setCountryId(null);
                    authorsListOldAuthors = em.merge(authorsListOldAuthors);
                }
            }
            for (Authors authorsListNewAuthors : authorsListNew)
            {
                if (!authorsListOld.contains(authorsListNewAuthors))
                {
                    Countries oldCountryIdOfAuthorsListNewAuthors = authorsListNewAuthors.getCountryId();
                    authorsListNewAuthors.setCountryId(countries);
                    authorsListNewAuthors = em.merge(authorsListNewAuthors);
                    if (oldCountryIdOfAuthorsListNewAuthors != null && !oldCountryIdOfAuthorsListNewAuthors.equals(countries))
                    {
                        oldCountryIdOfAuthorsListNewAuthors.getAuthorsList().remove(authorsListNewAuthors);
                        oldCountryIdOfAuthorsListNewAuthors = em.merge(oldCountryIdOfAuthorsListNewAuthors);
                    }
                }
            }
            for (BookTitles bookTitlesListOldBookTitles : bookTitlesListOld)
            {
                if (!bookTitlesListNew.contains(bookTitlesListOldBookTitles))
                {
                    bookTitlesListOldBookTitles.setCountryId(null);
                    bookTitlesListOldBookTitles = em.merge(bookTitlesListOldBookTitles);
                }
            }
            for (BookTitles bookTitlesListNewBookTitles : bookTitlesListNew)
            {
                if (!bookTitlesListOld.contains(bookTitlesListNewBookTitles))
                {
                    Countries oldCountryIdOfBookTitlesListNewBookTitles = bookTitlesListNewBookTitles.getCountryId();
                    bookTitlesListNewBookTitles.setCountryId(countries);
                    bookTitlesListNewBookTitles = em.merge(bookTitlesListNewBookTitles);
                    if (oldCountryIdOfBookTitlesListNewBookTitles != null && !oldCountryIdOfBookTitlesListNewBookTitles.equals(countries))
                    {
                        oldCountryIdOfBookTitlesListNewBookTitles.getBookTitlesList().remove(bookTitlesListNewBookTitles);
                        oldCountryIdOfBookTitlesListNewBookTitles = em.merge(oldCountryIdOfBookTitlesListNewBookTitles);
                    }
                }
            }
            for (Readers readersListOldReaders : readersListOld)
            {
                if (!readersListNew.contains(readersListOldReaders))
                {
                    readersListOldReaders.setCountryId(null);
                    readersListOldReaders = em.merge(readersListOldReaders);
                }
            }
            for (Readers readersListNewReaders : readersListNew)
            {
                if (!readersListOld.contains(readersListNewReaders))
                {
                    Countries oldCountryIdOfReadersListNewReaders = readersListNewReaders.getCountryId();
                    readersListNewReaders.setCountryId(countries);
                    readersListNewReaders = em.merge(readersListNewReaders);
                    if (oldCountryIdOfReadersListNewReaders != null && !oldCountryIdOfReadersListNewReaders.equals(countries))
                    {
                        oldCountryIdOfReadersListNewReaders.getReadersList().remove(readersListNewReaders);
                        oldCountryIdOfReadersListNewReaders = em.merge(oldCountryIdOfReadersListNewReaders);
                    }
                }
            }
            for (Publishers publishersListOldPublishers : publishersListOld)
            {
                if (!publishersListNew.contains(publishersListOldPublishers))
                {
                    publishersListOldPublishers.setCountryId(null);
                    publishersListOldPublishers = em.merge(publishersListOldPublishers);
                }
            }
            for (Publishers publishersListNewPublishers : publishersListNew)
            {
                if (!publishersListOld.contains(publishersListNewPublishers))
                {
                    Countries oldCountryIdOfPublishersListNewPublishers = publishersListNewPublishers.getCountryId();
                    publishersListNewPublishers.setCountryId(countries);
                    publishersListNewPublishers = em.merge(publishersListNewPublishers);
                    if (oldCountryIdOfPublishersListNewPublishers != null && !oldCountryIdOfPublishersListNewPublishers.equals(countries))
                    {
                        oldCountryIdOfPublishersListNewPublishers.getPublishersList().remove(publishersListNewPublishers);
                        oldCountryIdOfPublishersListNewPublishers = em.merge(oldCountryIdOfPublishersListNewPublishers);
                    }
                }
            }
            for (BookRequests bookRequestsListOldBookRequests : bookRequestsListOld)
            {
                if (!bookRequestsListNew.contains(bookRequestsListOldBookRequests))
                {
                    bookRequestsListOldBookRequests.setCountryId(null);
                    bookRequestsListOldBookRequests = em.merge(bookRequestsListOldBookRequests);
                }
            }
            for (BookRequests bookRequestsListNewBookRequests : bookRequestsListNew)
            {
                if (!bookRequestsListOld.contains(bookRequestsListNewBookRequests))
                {
                    Countries oldCountryIdOfBookRequestsListNewBookRequests = bookRequestsListNewBookRequests.getCountryId();
                    bookRequestsListNewBookRequests.setCountryId(countries);
                    bookRequestsListNewBookRequests = em.merge(bookRequestsListNewBookRequests);
                    if (oldCountryIdOfBookRequestsListNewBookRequests != null && !oldCountryIdOfBookRequestsListNewBookRequests.equals(countries))
                    {
                        oldCountryIdOfBookRequestsListNewBookRequests.getBookRequestsList().remove(bookRequestsListNewBookRequests);
                        oldCountryIdOfBookRequestsListNewBookRequests = em.merge(oldCountryIdOfBookRequestsListNewBookRequests);
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

    public static void destroy(Integer id) throws NonexistentEntityException
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
            List<Staffs> staffsList = countries.getStaffsList();
            for (Staffs staffsListStaffs : staffsList)
            {
                staffsListStaffs.setCountryId(null);
                staffsListStaffs = em.merge(staffsListStaffs);
            }
            List<Authors> authorsList = countries.getAuthorsList();
            for (Authors authorsListAuthors : authorsList)
            {
                authorsListAuthors.setCountryId(null);
                authorsListAuthors = em.merge(authorsListAuthors);
            }
            List<BookTitles> bookTitlesList = countries.getBookTitlesList();
            for (BookTitles bookTitlesListBookTitles : bookTitlesList)
            {
                bookTitlesListBookTitles.setCountryId(null);
                bookTitlesListBookTitles = em.merge(bookTitlesListBookTitles);
            }
            List<Readers> readersList = countries.getReadersList();
            for (Readers readersListReaders : readersList)
            {
                readersListReaders.setCountryId(null);
                readersListReaders = em.merge(readersListReaders);
            }
            List<Publishers> publishersList = countries.getPublishersList();
            for (Publishers publishersListPublishers : publishersList)
            {
                publishersListPublishers.setCountryId(null);
                publishersListPublishers = em.merge(publishersListPublishers);
            }
            List<BookRequests> bookRequestsList = countries.getBookRequestsList();
            for (BookRequests bookRequestsListBookRequests : bookRequestsList)
            {
                bookRequestsListBookRequests.setCountryId(null);
                bookRequestsListBookRequests = em.merge(bookRequestsListBookRequests);
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

    public static List<Countries> findCountriesEntities()
    {
        return findCountriesEntities(true, -1, -1);
    }

    public static List<Countries> findCountriesEntities(int maxResults, int firstResult)
    {
        return findCountriesEntities(false, maxResults, firstResult);
    }

    private static List<Countries> findCountriesEntities(boolean all, int maxResults, int firstResult)
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

    public static Countries findCountries(Integer id)
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

    public static int getCountriesCount()
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
    
    public static Countries findCountries(String name)
    {
        EntityManager em = getEntityManager();
        try
        {
            TypedQuery<Countries> query = em.createNamedQuery("Countries.findByName", Countries.class);
            
            query.setParameter("name", name);
            
            Countries obj;
            
            var result = query.getSingleResult();
            
            if (result != null)
            {
                obj = result;
            }
            else
            {
                obj = null;
            }
            
            return obj;
        }
        finally
        {
            em.close();
        }
    }
    
}
