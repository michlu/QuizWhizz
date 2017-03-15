package com.pw.quizwhizz;

import com.pw.quizwhizz.model.Role;
import com.pw.quizwhizz.model.User;
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

        entityManager.getTransaction().begin();

        entityManager.persist(roleAdmin);
        entityManager.persist(roleUser);
        entityManager.persist(adminNowin);
        entityManager.persist(userJacek);
        entityManager.persist(userAntoni);

        entityManager.getTransaction().commit();

        entityManager.close();
        DBConfig.closeFactory();


    }
}
