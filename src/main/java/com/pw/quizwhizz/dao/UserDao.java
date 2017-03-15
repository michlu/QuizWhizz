package com.pw.quizwhizz.dao;

import com.pw.quizwhizz.model.Role;
import com.pw.quizwhizz.model.User;
import com.pw.quizwhizz.utils.DBConfig;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michlu on 14.03.17.
 */
public class UserDao extends GenericDaoAbstract {

    public UserDao(Class type) {
        super(type);
    }

    public void addUser(String userLogin ,String firstName, String password){
        Role roleUser = Role.user();
        List<Role> userAccess = new ArrayList<>();

        User user = new User();
        user.setUserLogin(userLogin);
        user.setFirstName(firstName);
        user.setPassword(password);
        user.setRole(userAccess);
        super.saveOrUpdate(user);
    }

    public User findUserByLogin(String login){
        TypedQuery<User> userByLogin =
                DBConfig.createEntityManager().createQuery("SELECT u FROM User u WHERE u.userLogin = :login", User.class);
        userByLogin.setParameter("login", login);

        return userByLogin.getSingleResult();
    }

}
