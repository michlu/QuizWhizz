package com.pw.quizwhizz;

import com.pw.quizwhizz.dao.QuestionDao;
import com.pw.quizwhizz.model.*;
import com.pw.quizwhizz.utils.DBConfig;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Klasa ustawiajaca poczatkowe dane w bazie MySQL
 * Przy pierwszym uruchomieniu zmienic w pliku persistence.xml hibernate.hbm2ddl.auto - na "create"
 * w celu stworzenia struktury tabel bazy danych. Nastepnie powrocic do wartosci "update"
 */
public class Main {
    public static void main(String[] args) {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db_quizwhizz");
        EntityManager entityManager = DBConfig.createEntityManager();


        Role roleUser = Role.user();
        List<Role> userAccess = new ArrayList<>();
        userAccess.add(roleUser);
        Role roleAdmin = Role.admin();
        List<Role> fullAccess = new ArrayList<>();
        fullAccess.add(roleUser);
        fullAccess.add(roleAdmin);

        User adminNowin = new User();
        adminNowin.setUserLogin("nowin");
        adminNowin.setFirstName("Michał");
        adminNowin.setPassword("qwe123");
        adminNowin.setMail("michlu@o2.pl");
        adminNowin.setRegDate(new Date());
        adminNowin.setRole(fullAccess);
//        adminNowin.addRole(Role.admin());
//        adminNowin.addRole(Role.user());

        User userJacek = new User();
        userJacek.setUserLogin("jck");
        userJacek.setFirstName("Jacek");
        userJacek.setPassword("qwe123");
        userJacek.setMail("jacek@serduszko.pl");
        userJacek.setRegDate(new Date());
        userJacek.setRole(userAccess);
//        userJacek.addRole(Role.user());

        User userAntoni = new User();
        userAntoni.setUserLogin("ant");
        userAntoni.setFirstName("Antoni");
        userAntoni.setPassword("qwe123");
        userAntoni.setMail("antoni@przegosc.pl");
        userAntoni.setRegDate(new Date());
        userAntoni.setRole(userAccess);

        Subject subjectJava = new Subject();
        subjectJava.setSubjectName("Java");
        subjectJava.setDescription("Pytania z zakresu programowania w jezyku Java.");

        Subject subjectGeography = new Subject();
        subjectGeography.setSubjectName("Geografia");
        subjectGeography.setDescription("Pytania z zakresu  nauk przyrodniczych (geografia fizyczna) oraz do nauk społeczno-ekonomicznych (geografia społeczno-ekonomiczna).");

        // dodanie pytania do bazy
        Subject subjectTest = new Subject();
        subjectTest.setSubjectName("Wszystko i nic");
        subjectTest.setDescription("Pytania z zakresu bez odpowiedzi.");
        Question question = new Question();
        question.setQuestion("Ktora odpowiedz jest poprawna?");
        question.setSubject(subjectTest);
        Answer answer1 = new Answer();
        answer1.setAnswer("odpowiedz pierwsza");
        Answer answer2 = new Answer();
        answer2.setAnswer("odpowiedz druga");
        answer2.setCorrect(true);
        Answer answer3 = new Answer();
        answer3.setAnswer("odpowiedz trzecia");
        Answer answer4 = new Answer();
        answer4.setAnswer("odpowiedz czwarta");
        question.addAnswer(answer1);
        question.addAnswer(answer2);
        question.addAnswer(answer3);
        question.addAnswer(answer4);
        QuestionDao questionDao = new QuestionDao(Question.class);
        questionDao.addQuestion(question, subjectTest, answer1, answer2, answer3, answer4);

        // wyszukanie pytania
        Question findQuestion = (Question) questionDao.find(1L);
        System.out.println(findQuestion);
        findQuestion.getAnswer().forEach(System.out::println);


        // Comitowanie danych do bazy
        entityManager.getTransaction().begin();

        entityManager.persist(roleAdmin);
        entityManager.persist(roleUser);
        entityManager.persist(adminNowin);
        entityManager.persist(userJacek);
        entityManager.persist(userAntoni);

        entityManager.persist(subjectJava);
        entityManager.persist(subjectGeography);

        entityManager.getTransaction().commit();

        entityManager.close();
        DBConfig.closeFactory();


    }
}
