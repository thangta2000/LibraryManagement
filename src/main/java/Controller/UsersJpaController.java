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
import Model.Roles;
import Model.Staffs;
import Model.Users;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class UsersJpaController implements Serializable
{

    public UsersJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Users users)
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles roleId = users.getRoleId();
            if (roleId != null)
            {
                roleId = em.getReference(roleId.getClass(), roleId.getId());
                users.setRoleId(roleId);
            }
            Staffs staffId = users.getStaffId();
            if (staffId != null)
            {
                staffId = em.getReference(staffId.getClass(), staffId.getId());
                users.setStaffId(staffId);
            }
            em.persist(users);
            if (roleId != null)
            {
                roleId.getUsersCollection().add(users);
                roleId = em.merge(roleId);
            }
            if (staffId != null)
            {
                staffId.getUsersCollection().add(users);
                staffId = em.merge(staffId);
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

    public void edit(Users users) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getId());
            Roles roleIdOld = persistentUsers.getRoleId();
            Roles roleIdNew = users.getRoleId();
            Staffs staffIdOld = persistentUsers.getStaffId();
            Staffs staffIdNew = users.getStaffId();
            if (roleIdNew != null)
            {
                roleIdNew = em.getReference(roleIdNew.getClass(), roleIdNew.getId());
                users.setRoleId(roleIdNew);
            }
            if (staffIdNew != null)
            {
                staffIdNew = em.getReference(staffIdNew.getClass(), staffIdNew.getId());
                users.setStaffId(staffIdNew);
            }
            users = em.merge(users);
            if (roleIdOld != null && !roleIdOld.equals(roleIdNew))
            {
                roleIdOld.getUsersCollection().remove(users);
                roleIdOld = em.merge(roleIdOld);
            }
            if (roleIdNew != null && !roleIdNew.equals(roleIdOld))
            {
                roleIdNew.getUsersCollection().add(users);
                roleIdNew = em.merge(roleIdNew);
            }
            if (staffIdOld != null && !staffIdOld.equals(staffIdNew))
            {
                staffIdOld.getUsersCollection().remove(users);
                staffIdOld = em.merge(staffIdOld);
            }
            if (staffIdNew != null && !staffIdNew.equals(staffIdOld))
            {
                staffIdNew.getUsersCollection().add(users);
                staffIdNew = em.merge(staffIdNew);
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = users.getId();
                if (findUsers(id) == null)
                {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
            Users users;
            try
            {
                users = em.getReference(Users.class, id);
                users.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            Roles roleId = users.getRoleId();
            if (roleId != null)
            {
                roleId.getUsersCollection().remove(users);
                roleId = em.merge(roleId);
            }
            Staffs staffId = users.getStaffId();
            if (staffId != null)
            {
                staffId.getUsersCollection().remove(users);
                staffId = em.merge(staffId);
            }
            em.remove(users);
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

    public List<Users> findUsersEntities()
    {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult)
    {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Users.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getUsersCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
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