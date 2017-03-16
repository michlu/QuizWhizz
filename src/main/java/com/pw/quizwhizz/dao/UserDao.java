package com.pw.quizwhizz.dao;

import com.pw.quizwhizz.model.Role;
import com.pw.quizwhizz.model.User;
import com.pw.quizwhizz.utils.DBConfig;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


public class UserDao extends GenericDaoAbstract {
    private EntityManager entityManager = super.getEntityManager();
    private EntityTransaction transaction = super.getTransaction();

    public UserDao(Class type) {
        super(type);
    }

    public Boolean addUser(User user, Role role) {
        try {
            transaction.begin();
            entityManager.persist(role);
            entityManager.persist(user);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            handleException(e);
            return false;
        } finally {
            entityManager.close();
        }
    }

    public User findUserByLogin(String login){
        TypedQuery<User> userByLogin =
                DBConfig.createEntityManager().createQuery("SELECT u FROM User u WHERE u.userLogin = :login", User.class);
        userByLogin.setParameter("login", login);

        return userByLogin.getSingleResult();
    }

}
