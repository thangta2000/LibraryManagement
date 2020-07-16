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
import Model.Staffs;
import Model.Users;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class StaffsJpaController implements Serializable
{

    public StaffsJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Staffs staffs)
    {
        if (staffs.getUsersCollection() == null)
        {
            staffs.setUsersCollection(new ArrayList<Users>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Countries countryId = staffs.getCountryId();
            if (countryId != null)
            {
                countryId = em.getReference(countryId.getClass(), countryId.getId());
                staffs.setCountryId(countryId);
            }
            Collection<Users> attachedUsersCollection = new ArrayList<Users>();
            for (Users usersCollectionUsersToAttach : staffs.getUsersCollection())
            {
                usersCollectionUsersToAttach = em.getReference(usersCollectionUsersToAttach.getClass(), usersCollectionUsersToAttach.getId());
                attachedUsersCollection.add(usersCollectionUsersToAttach);
            }
            staffs.setUsersCollection(attachedUsersCollection);
            em.persist(staffs);
            if (countryId != null)
            {
                countryId.getStaffsCollection().add(staffs);
                countryId = em.merge(countryId);
            }
            for (Users usersCollectionUsers : staffs.getUsersCollection())
            {
                Staffs oldStaffIdOfUsersCollectionUsers = usersCollectionUsers.getStaffId();
                usersCollectionUsers.setStaffId(staffs);
                usersCollectionUsers = em.merge(usersCollectionUsers);
                if (oldStaffIdOfUsersCollectionUsers != null)
                {
                    oldStaffIdOfUsersCollectionUsers.getUsersCollection().remove(usersCollectionUsers);
                    oldStaffIdOfUsersCollectionUsers = em.merge(oldStaffIdOfUsersCollectionUsers);
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

    public void edit(Staffs staffs) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Staffs persistentStaffs = em.find(Staffs.class, staffs.getId());
            Countries countryIdOld = persistentStaffs.getCountryId();
            Countries countryIdNew = staffs.getCountryId();
            Collection<Users> usersCollectionOld = persistentStaffs.getUsersCollection();
            Collection<Users> usersCollectionNew = staffs.getUsersCollection();
            if (countryIdNew != null)
            {
                countryIdNew = em.getReference(countryIdNew.getClass(), countryIdNew.getId());
                staffs.setCountryId(countryIdNew);
            }
            Collection<Users> attachedUsersCollectionNew = new ArrayList<Users>();
            for (Users usersCollectionNewUsersToAttach : usersCollectionNew)
            {
                usersCollectionNewUsersToAttach = em.getReference(usersCollectionNewUsersToAttach.getClass(), usersCollectionNewUsersToAttach.getId());
                attachedUsersCollectionNew.add(usersCollectionNewUsersToAttach);
            }
            usersCollectionNew = attachedUsersCollectionNew;
            staffs.setUsersCollection(usersCollectionNew);
            staffs = em.merge(staffs);
            if (countryIdOld != null && !countryIdOld.equals(countryIdNew))
            {
                countryIdOld.getStaffsCollection().remove(staffs);
                countryIdOld = em.merge(countryIdOld);
            }
            if (countryIdNew != null && !countryIdNew.equals(countryIdOld))
            {
                countryIdNew.getStaffsCollection().add(staffs);
                countryIdNew = em.merge(countryIdNew);
            }
            for (Users usersCollectionOldUsers : usersCollectionOld)
            {
                if (!usersCollectionNew.contains(usersCollectionOldUsers))
                {
                    usersCollectionOldUsers.setStaffId(null);
                    usersCollectionOldUsers = em.merge(usersCollectionOldUsers);
                }
            }
            for (Users usersCollectionNewUsers : usersCollectionNew)
            {
                if (!usersCollectionOld.contains(usersCollectionNewUsers))
                {
                    Staffs oldStaffIdOfUsersCollectionNewUsers = usersCollectionNewUsers.getStaffId();
                    usersCollectionNewUsers.setStaffId(staffs);
                    usersCollectionNewUsers = em.merge(usersCollectionNewUsers);
                    if (oldStaffIdOfUsersCollectionNewUsers != null && !oldStaffIdOfUsersCollectionNewUsers.equals(staffs))
                    {
                        oldStaffIdOfUsersCollectionNewUsers.getUsersCollection().remove(usersCollectionNewUsers);
                        oldStaffIdOfUsersCollectionNewUsers = em.merge(oldStaffIdOfUsersCollectionNewUsers);
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
                Integer id = staffs.getId();
                if (findStaffs(id) == null)
                {
                    throw new NonexistentEntityException("The staffs with id " + id + " no longer exists.");
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
            Staffs staffs;
            try
            {
                staffs = em.getReference(Staffs.class, id);
                staffs.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The staffs with id " + id + " no longer exists.", enfe);
            }
            Countries countryId = staffs.getCountryId();
            if (countryId != null)
            {
                countryId.getStaffsCollection().remove(staffs);
                countryId = em.merge(countryId);
            }
            Collection<Users> usersCollection = staffs.getUsersCollection();
            for (Users usersCollectionUsers : usersCollection)
            {
                usersCollectionUsers.setStaffId(null);
                usersCollectionUsers = em.merge(usersCollectionUsers);
            }
            em.remove(staffs);
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

    public List<Staffs> findStaffsEntities()
    {
        return findStaffsEntities(true, -1, -1);
    }

    public List<Staffs> findStaffsEntities(int maxResults, int firstResult)
    {
        return findStaffsEntities(false, maxResults, firstResult);
    }

    private List<Staffs> findStaffsEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Staffs.class));
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

    public Staffs findStaffs(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Staffs.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getStaffsCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Staffs> rt = cq.from(Staffs.class);
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
