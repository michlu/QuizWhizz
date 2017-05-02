package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.category.Category;
import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.Game;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Created by Karolina on 30.04.2017.
 */
@Component
public class GameFactory {

    public Game build(Category category, List<Question> questions) throws IllegalNumberOfQuestionsException {
        return new Game(category, questions);
    }
}