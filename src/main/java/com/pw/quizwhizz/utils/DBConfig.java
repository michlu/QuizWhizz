package com.pw.quizwhizz.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Singleton tworzący jedna klase EntityManagerFactory i udostępniająca EntityMenager
 */
public class DBConfig {
    private static EntityManagerFactory entityManagerFactory;

    public static EntityManager createEntityManager(){
        System.out.println("DBConfig: build EntityManager'a");
        if(entityManagerFactory != null)
            return entityManagerFactory.createEntityManager();
        else
            entityManagerFactory = createEntityManagerFactory();
            return entityManagerFactory.createEntityManager();
    }

    private static EntityManagerFactory createEntityManagerFactory(){
        System.out.println("DBConfig: build EntityManagerFactory");
        if (entityManagerFactory == null)
            return Persistence.createEntityManagerFactory("db_quizwhizz");
        else
            return entityManagerFactory;
    }

    public static void closeFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
    public static void rollback(EntityTransaction tx) {
            if (tx != null) {
                tx.rollback();
            }
    }
}
