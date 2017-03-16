package com.pw.quizwhizz.dao;

import com.pw.quizwhizz.model.*;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by michlu on 16.03.17.
 */
public class QuestionDao extends GenericDaoAbstract  {
    private EntityManager entityManager = super.getEntityManager();
    private EntityTransaction transaction = super.getTransaction();

    public QuestionDao(Class type) {
        super(type);
    }

    public Boolean addQuestion(Question question, Subject subject, Answer ans1, Answer ans2, Answer ans3, Answer ans4) {
        try {
            transaction.begin();
            entityManager.persist(subject);
            entityManager.persist(ans1);
            entityManager.persist(ans2);
            entityManager.persist(ans3);
            entityManager.persist(ans4);
            entityManager.persist(question);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            handleException(e);
            return false;
        } finally {
            entityManager.close();
        }
    }
}
