package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.dao.GenericDao;
import com.pw.quizwhizz.dao.SubjectDao;
import com.pw.quizwhizz.model.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/subjectServlet")
public class SubjectController extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputSubject = request.getParameter("inputSubject");
        String inputDescription = request.getParameter("inputDescription");
        request.setCharacterEncoding("UTF-8");

        GenericDao<Subject> subjectGenericDao = new SubjectDao(Subject.class);
        Subject subject = new Subject(inputSubject, inputDescription);

        subjectGenericDao.saveOrUpdate(subject);

        System.out.println("SubjectController: add" + subject.toString());
        response.sendRedirect("admin_add.jsp");
    }
}
