package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Implementacja wzorca Factory dla gry, wykorzystywana w serwisie gry.
 *
 * @author Karolina Prusaczyk
 * @see com.pw.quizwhizz.service.impl.GameServiceImpl
 */
@Component
public class GameFactory {

    public Game build(Category category, List<Question> questions) throws IllegalNumberOfQuestionsException {
        return new Game(category, questions);
    }

    public Game build() {
        return new Game();
    }
}
