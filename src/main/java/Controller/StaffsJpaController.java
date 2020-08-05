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
import Utility.Factory;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class StaffsJpaController implements Serializable
{

//    public StaffsJpaController(EntityManagerFactory emf)
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

    public static void create(Staffs staffs)
    {
        if (staffs.getUsersList() == null)
        {
            staffs.setUsersList(new ArrayList<Users>());
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
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : staffs.getUsersList())
            {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getId());
                attachedUsersList.add(usersListUsersToAttach);
            }
            staffs.setUsersList(attachedUsersList);
            em.persist(staffs);
            if (countryId != null)
            {
                countryId.getStaffsList().add(staffs);
                countryId = em.merge(countryId);
            }
            for (Users usersListUsers : staffs.getUsersList())
            {
                Staffs oldStaffIdOfUsersListUsers = usersListUsers.getStaffId();
                usersListUsers.setStaffId(staffs);
                usersListUsers = em.merge(usersListUsers);
                if (oldStaffIdOfUsersListUsers != null)
                {
                    oldStaffIdOfUsersListUsers.getUsersList().remove(usersListUsers);
                    oldStaffIdOfUsersListUsers = em.merge(oldStaffIdOfUsersListUsers);
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

    public static void edit(Staffs staffs) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Staffs persistentStaffs = em.find(Staffs.class, staffs.getId());
            Countries countryIdOld = persistentStaffs.getCountryId();
            Countries countryIdNew = staffs.getCountryId();
            List<Users> usersListOld = persistentStaffs.getUsersList();
            List<Users> usersListNew = staffs.getUsersList();
            if (countryIdNew != null)
            {
                countryIdNew = em.getReference(countryIdNew.getClass(), countryIdNew.getId());
                staffs.setCountryId(countryIdNew);
            }
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew)
            {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getId());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            staffs.setUsersList(usersListNew);
            staffs = em.merge(staffs);
            if (countryIdOld != null && !countryIdOld.equals(countryIdNew))
            {
                countryIdOld.getStaffsList().remove(staffs);
                countryIdOld = em.merge(countryIdOld);
            }
            if (countryIdNew != null && !countryIdNew.equals(countryIdOld))
            {
                countryIdNew.getStaffsList().add(staffs);
                countryIdNew = em.merge(countryIdNew);
            }
            for (Users usersListOldUsers : usersListOld)
            {
                if (!usersListNew.contains(usersListOldUsers))
                {
                    usersListOldUsers.setStaffId(null);
                    usersListOldUsers = em.merge(usersListOldUsers);
                }
            }
            for (Users usersListNewUsers : usersListNew)
            {
                if (!usersListOld.contains(usersListNewUsers))
                {
                    Staffs oldStaffIdOfUsersListNewUsers = usersListNewUsers.getStaffId();
                    usersListNewUsers.setStaffId(staffs);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldStaffIdOfUsersListNewUsers != null && !oldStaffIdOfUsersListNewUsers.equals(staffs))
                    {
                        oldStaffIdOfUsersListNewUsers.getUsersList().remove(usersListNewUsers);
                        oldStaffIdOfUsersListNewUsers = em.merge(oldStaffIdOfUsersListNewUsers);
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

    public static void destroy(Integer id) throws NonexistentEntityException
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
                countryId.getStaffsList().remove(staffs);
                countryId = em.merge(countryId);
            }
            List<Users> usersList = staffs.getUsersList();
            for (Users usersListUsers : usersList)
            {
                usersListUsers.setStaffId(null);
                usersListUsers = em.merge(usersListUsers);
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

    public static List<Staffs> findStaffsEntities()
    {
        return findStaffsEntities(true, -1, -1);
    }

    public static List<Staffs> findStaffsEntities(int maxResults, int firstResult)
    {
        return findStaffsEntities(false, maxResults, firstResult);
    }

    private static List<Staffs> findStaffsEntities(boolean all, int maxResults, int firstResult)
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

    public static Staffs findStaffs(Integer id)
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

    public static int getStaffsCount()
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
    
    public static Staffs add(Staffs staff)
    {
        create(staff);
        return staff;
    }
    
}
