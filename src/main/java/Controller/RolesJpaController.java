/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.Roles;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class RolesJpaController implements Serializable
{

//    public RolesJpaController(EntityManagerFactory emf)
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

    public static void create(Roles roles)
    {
        if (roles.getUsersList() == null)
        {
            roles.setUsersList(new ArrayList<Users>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : roles.getUsersList())
            {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getId());
                attachedUsersList.add(usersListUsersToAttach);
            }
            roles.setUsersList(attachedUsersList);
            em.persist(roles);
            for (Users usersListUsers : roles.getUsersList())
            {
                Roles oldRoleIdOfUsersListUsers = usersListUsers.getRoleId();
                usersListUsers.setRoleId(roles);
                usersListUsers = em.merge(usersListUsers);
                if (oldRoleIdOfUsersListUsers != null)
                {
                    oldRoleIdOfUsersListUsers.getUsersList().remove(usersListUsers);
                    oldRoleIdOfUsersListUsers = em.merge(oldRoleIdOfUsersListUsers);
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

    public static void  edit(Roles roles) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles persistentRoles = em.find(Roles.class, roles.getId());
            List<Users> usersListOld = persistentRoles.getUsersList();
            List<Users> usersListNew = roles.getUsersList();
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew)
            {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getId());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            roles.setUsersList(usersListNew);
            roles = em.merge(roles);
            for (Users usersListOldUsers : usersListOld)
            {
                if (!usersListNew.contains(usersListOldUsers))
                {
                    usersListOldUsers.setRoleId(null);
                    usersListOldUsers = em.merge(usersListOldUsers);
                }
            }
            for (Users usersListNewUsers : usersListNew)
            {
                if (!usersListOld.contains(usersListNewUsers))
                {
                    Roles oldRoleIdOfUsersListNewUsers = usersListNewUsers.getRoleId();
                    usersListNewUsers.setRoleId(roles);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldRoleIdOfUsersListNewUsers != null && !oldRoleIdOfUsersListNewUsers.equals(roles))
                    {
                        oldRoleIdOfUsersListNewUsers.getUsersList().remove(usersListNewUsers);
                        oldRoleIdOfUsersListNewUsers = em.merge(oldRoleIdOfUsersListNewUsers);
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
                Integer id = roles.getId();
                if (findRoles(id) == null)
                {
                    throw new NonexistentEntityException("The roles with id " + id + " no longer exists.");
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
            Roles roles;
            try
            {
                roles = em.getReference(Roles.class, id);
                roles.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The roles with id " + id + " no longer exists.", enfe);
            }
            List<Users> usersList = roles.getUsersList();
            for (Users usersListUsers : usersList)
            {
                usersListUsers.setRoleId(null);
                usersListUsers = em.merge(usersListUsers);
            }
            em.remove(roles);
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

    public static List<Roles> findRolesEntities()
    {
        return findRolesEntities(true, -1, -1);
    }

    public static List<Roles> findRolesEntities(int maxResults, int firstResult)
    {
        return findRolesEntities(false, maxResults, firstResult);
    }

    private static List<Roles> findRolesEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Roles.class));
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

    public static Roles findRoles(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Roles.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public static int getRolesCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Roles> rt = cq.from(Roles.class);
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
