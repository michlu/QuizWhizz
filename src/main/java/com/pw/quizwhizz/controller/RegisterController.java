package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.dao.GenericDao;
import com.pw.quizwhizz.dao.UserDao;
import com.pw.quizwhizz.model.Role;
import com.pw.quizwhizz.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/registerServlet")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userLogin = request.getParameter("inputLogin");
        String userName = request.getParameter("inputName");
        String password = request.getParameter("inputPassword");
        String email = request.getParameter("inputEmail");
        request.setCharacterEncoding("UTF-8");
        System.out.println(userLogin + " " +  userName  + " " +  password  + " " +  email);

        GenericDao<User> userDao = new UserDao(User.class);

        Role roleUser = Role.user();
        List<Role> userAccess = new ArrayList<>();
        userAccess.add(roleUser);

        User user = new User();
        user.setUserLogin(userLogin);
        user.setFirstName(userName);
        user.setPassword(password);
        user.setRole(userAccess);
        user.setMail(email);
        user.setRegDate(new Date());

        userDao.saveOrUpdate(user);


        response.sendRedirect("index.jsp");
    }

}