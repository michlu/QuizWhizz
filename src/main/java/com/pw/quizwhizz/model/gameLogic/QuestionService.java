package com.pw.quizwhizz.model.gameLogic;

import com.pw.quizwhizz.model.gameLogic.entity.Category;
import com.pw.quizwhizz.model.gameLogic.entity.Question;

import java.util.List;

/**
 * Created by Karolina on 26.03.2017.
 */
public interface QuestionService {
    public List<Question> get10RandomQuestions(Category category);
}
