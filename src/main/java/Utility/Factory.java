/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author admin
 */
public class Factory
{
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_LibraryManagement_jar_1.0-SNAPSHOTPU");
    
    public static EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }
}
