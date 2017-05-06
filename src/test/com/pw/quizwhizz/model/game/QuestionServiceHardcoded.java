package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.dto.game.CategoryDTO;
import com.pw.quizwhizz.model.game.Answer;
import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.model.game.Question;
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
    public List<Question> getQuestionsForNewGame(long categoryId) {
        return null;
    }

    @Override
    public List<Question> findAllByCategoryId(long categoryId) {
        return null;
    }

    @Override
    public List<Question> findAllByCategory(Category categoryDTO) {
        return null;
    }

    @Override
    public List<Question> getRandomQuestionsByCategoryId(long categoryId, int number) {
        List<Question> questions = new ArrayList<>();

        List<Question> allQuestions = createQuestions(category);
        int size = allQuestions.size();

        Question q;

        if (size >= number) {
            for (int i = 0; i < number; i++) {
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
    public List<Question> getRandomQuestionsByCategory(Category category, int number) {
        long id = category.getId();
        return getRandomQuestionsByCategoryId(id, number);
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
    public void updateQuestion(String inputId, String inputQuestion, String inputAnswer1, String inputAnswer2, String inputAnswer3, String inputAnswer4, String answerCorrect) {

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