package com.pw.quizwhizz.model;

import com.pw.quizwhizz.model.entity.Question;
import com.pw.quizwhizz.model.entity.Category;

import java.util.List;

/**
 * Created by Karolina on 26.03.2017.
 */
public interface QuestionService {
    public List<Question> get10RandomQuestions(Category category);
}
