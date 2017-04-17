package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.entity.Category;
import com.pw.quizwhizz.model.entity.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getRandomQuestions(Category category, int number);
    List<Question> get10RandomQuestions(Category category);
    List<Question> findAllByCategory(Category category);
    Question findById(Long Id);
    void deleteById(Long Id);
    void addQuestion(String categoryId, String inputQuestion, String inputAnswer1, String inputAnswer2, String inputAnswer3, String inputAnswer4, String answerCorrect);
    void updateQuestion(String inputId, String inputQuestion, String inputAnswer1, String answerId1, String inputAnswer2, String answerId2, String inputAnswer3, String answerId3, String inputAnswer4, String answerId4, String answerCorrect);
}
