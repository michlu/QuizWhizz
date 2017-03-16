package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.dao.GenericDao;
import com.pw.quizwhizz.dao.QuestionDao;
import com.pw.quizwhizz.model.Answer;
import com.pw.quizwhizz.model.Question;
import com.pw.quizwhizz.model.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/questionServlet")
public class QuestionController extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputQuestionSubject = request.getParameter("inputQuestion_subject");
        String inputQuestion = request.getParameter("inputQuestion");
        String inputAnswer1 = request.getParameter("inputAnswer_1");
        String inputAnswer2 = request.getParameter("inputAnswer_2");
        String inputAnswer3 = request.getParameter("inputAnswer_3");
        String inputAnswer4 = request.getParameter("inputAnswer_4");
        String answerCorrect = request.getParameter("answer_correct");
        request.setCharacterEncoding("UTF-8");
        System.out.println(inputQuestionSubject + " " +  inputQuestion);
        System.out.println(inputAnswer1 + " " +  inputAnswer2 + " " + inputAnswer3 + " " +  inputAnswer4);
        System.out.println(answerCorrect);

        //TODO stworzyc podlinkowanie tematow do pytan
        Subject subject = new Subject();
        subject.setSubjectName(inputQuestionSubject);


        Question question = new Question();
//        question.setSubject(subject);
        question.setQuestion(inputQuestion);

        Answer answer1 = new Answer();
        answer1.setAnswer(inputAnswer1);
        Answer answer2 = new Answer();
        answer2.setAnswer(inputAnswer2);
        Answer answer3 = new Answer();
        answer3.setAnswer(inputAnswer3);
        Answer answer4 = new Answer();
        answer4.setAnswer(inputAnswer4);

        if("correct_1".equals(answerCorrect))
            answer1.setCorrect(true);
        System.out.println("poprawne 1");
            answer2.setCorrect(false);
            answer3.setCorrect(false);
            answer4.setCorrect(false);
        if("correct_2".equals(answerCorrect))
            answer2.setCorrect(true);
        System.out.println("poprawne 1");
            answer1.setCorrect(false);
            answer3.setCorrect(false);
            answer4.setCorrect(false);
        if("correct_3".equals(answerCorrect))
            answer3.setCorrect(true);
        System.out.println("poprawne 1");
            answer1.setCorrect(false);
            answer2.setCorrect(false);
            answer4.setCorrect(false);
        if("correct_4".equals(answerCorrect))
            answer4.setCorrect(true);
        System.out.println("poprawne 1");
            answer1.setCorrect(false);
            answer2.setCorrect(false);
            answer3.setCorrect(false);

        question.addAnswer(answer1);
        question.addAnswer(answer2);
        question.addAnswer(answer3);
        question.addAnswer(answer4);

//        GenericDao<Question> genericDao = new QuestionDao(Question.class);
//        genericDao.saveOrUpdate(question);

        QuestionDao questionDao = new QuestionDao(Question.class);

        questionDao.addQuestion(question, subject, answer1, answer2, answer3, answer4);



        response.sendRedirect("admin_add.jsp");
    }
}
