package com.pw.quizwhizz.service;

import com.pw.quizwhizz.dto.game.CategoryDTO;
import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.model.game.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestionsForNewGame(long categoryId);
    List<Question> getRandomQuestionsByCategoryId(long categoryId, int number);
    List<Question> getRandomQuestionsByCategory(Category category, int number);
    List<Question> findAllByCategoryId(long categoryId);
    List<Question> findAllByCategory(Category category);

    Question findById(Long Id);
    void deleteById(Long Id);
    void addQuestion(String categoryId, String inputQuestion, String inputAnswer1, String inputAnswer2, String inputAnswer3, String inputAnswer4, String answerCorrect);
    void updateQuestion(String inputId, String inputQuestion, String inputAnswer1, String inputAnswer2, String inputAnswer3, String inputAnswer4, String answerCorrect);
}
