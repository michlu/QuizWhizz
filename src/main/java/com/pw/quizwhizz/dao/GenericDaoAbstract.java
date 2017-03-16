package com.pw.quizwhizz.dao;

import com.pw.quizwhizz.utils.DBConfig;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Generyczna klasa Data Access Object udostępniająca podstawowe metody obslugi bazdy danych
 * @param <T>
 */
public abstract class GenericDaoAbstract<T> implements GenericDao<T> {
    private EntityManager entityManager;
    private EntityTransaction transaction;

    private final Class<T> entityClass;

    public GenericDaoAbstract(Class<T> type) {
        this.entityManager = DBConfig.createEntityManager();
        this.entityClass = type;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityTransaction getTransaction() {
        if(transaction==null)
            transaction = entityManager.getTransaction();
        return transaction;
    }

    @Override
    public Boolean saveOrUpdate(T entity) {
        try {
            startOperation();
            entityManager.persist(entityManager.contains(entity) ? entity : entityManager.merge(entity));
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            handleException(e);
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean delete(T entity) {
        try {
            startOperation();
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
            transaction.commit();
            return true;

        } catch (Exception e) {
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public T find(Long id) {
        T obj = null;
        try {
            startOperation();
            obj = (T) entityManager.find(entityClass, id);
            transaction.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            entityManager.close();
        }
        return obj;
    }

    @Override
    public List<T> findAll() {
        List<T>  entities = null;
        try {
            startOperation();
            TypedQuery<T> query = (TypedQuery<T>) entityManager.createQuery("FROM " + entityClass.getName());
            entities = query.getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            entityManager.close();
        }
        return entities;
    }

    protected void startOperation() throws HibernateException {
        entityManager = DBConfig.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    protected void handleException(HibernateException e) throws DataAccessLayerException {
        DBConfig.rollback(transaction);
        throw new DataAccessLayerException(e);
    }
}