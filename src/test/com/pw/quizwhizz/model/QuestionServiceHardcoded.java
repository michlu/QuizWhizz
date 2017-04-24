package com.pw.quizwhizz.model;

import com.pw.quizwhizz.model.entity.Answer;
import com.pw.quizwhizz.model.entity.Category;
import com.pw.quizwhizz.model.entity.Question;
import com.pw.quizwhizz.service.QuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Karolina on 26.03.2017.
 */
public class QuestionServiceHardcoded implements QuestionService {

    private Random random = new Random();
    private Category category = new Category("Category1");

    @Override
    public List<Question> getRandomQuestions(Category category, int number) {
        return null;
    }

    @Override
    public List<Question> get10RandomQuestions(Category category) {
        List<Question> questions = new ArrayList<>();

        List<Question> allQuestions = createQuestions(category);
        int size = allQuestions.size();

        Question q;

        if (size >= 10) {
            for (int i = 0; i < 10; i++) {
                q = allQuestions.get(random.nextInt(size));
                if (!questions.contains(q)) {
                    questions.add(q);
                } else {
                    i--;
                }
            }
        }
        return questions;
    }

    @Override
    public List<Question> findAllByCategory(Category category) {
        return null;
    }

    @Override
    public Question findById(Long Id) {
        return null;
    }

    @Override
    public void deleteById(Long Id) {

    }

    @Override
    public void addQuestion(String categoryId, String inputQuestion, String inputAnswer1, String inputAnswer2, String inputAnswer3, String inputAnswer4, String answerCorrect) {

    }

    @Override
    public void updateQuestion(String inputId, String inputQuestion, String inputAnswer1, String answerId1, String inputAnswer2, String answerId2, String inputAnswer3, String answerId3, String inputAnswer4, String answerId4, String answerCorrect) {

    }

    private List<Question> createQuestions(Category category) {
        List<Question> allQuestions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();
        Answer answer1 = new Answer("1", false);
        Answer answer2 = new Answer("2", true);
        Answer answer3 = new Answer("3", false);
        Answer answer4 = new Answer("4", false);

        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);

        Question question1 = new Question("Question 1", category, answers);
        Question question2 = new Question("Question 2", category, answers);
        Question question3 = new Question("Question 3", category, answers);
        Question question4 = new Question("Question 4", category, answers);
        Question question5 = new Question("Question 5", category, answers);
        Question question6 = new Question("Question 6", category, answers);
        Question question7 = new Question("Question 7", category, answers);
        Question question8 = new Question("Question 8", category, answers);
        Question question9 = new Question("Question 9", category, answers);
        Question question10 = new Question("Question 10", category, answers);
        Question question11 = new Question("Question 11", category, answers);
        Question question12 = new Question("Question 12", category, answers);

        allQuestions.add(question1);
        allQuestions.add(question2);
        allQuestions.add(question3);
        allQuestions.add(question4);
        allQuestions.add(question5);
        allQuestions.add(question6);
        allQuestions.add(question7);
        allQuestions.add(question8);
        allQuestions.add(question9);
        allQuestions.add(question10);
        allQuestions.add(question11);
        allQuestions.add(question12);

        return allQuestions;
    }

}