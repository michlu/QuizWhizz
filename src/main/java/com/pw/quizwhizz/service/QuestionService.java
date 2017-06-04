package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.model.game.Question;
import com.pw.quizwhizz.service.exception.NoQuestionsInDBException;

import java.util.List;

/**
 * Abstrakcyjna warstwa serwisu
 * @author Michał Nowiński, Karolina Prusaczyk
 */
public interface QuestionService {
    List<Question> getQuestionsForNewGame(long categoryId) throws IllegalNumberOfQuestionsException, NoQuestionsInDBException;
    List<Question> getRandomQuestionsByCategoryId(long categoryId, int number) throws IllegalNumberOfQuestionsException, NoQuestionsInDBException;
    List<Question> getRandomQuestionsByCategory(Category category, int number) throws IllegalNumberOfQuestionsException, NoQuestionsInDBException;
    List<Question> findAllByCategoryId(long categoryId);

    Question findById(Long Id);
    void deleteById(Long Id);
    void addQuestion(String categoryId, String inputQuestion, String inputAnswer1, String inputAnswer2, String inputAnswer3, String inputAnswer4, String answerCorrect);
    void updateQuestion(String inputId, String inputQuestion, String inputAnswer1, String inputAnswer2, String inputAnswer3, String inputAnswer4, String answerCorrect);
}
