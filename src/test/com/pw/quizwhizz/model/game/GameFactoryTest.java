package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.GameFactory;
import com.pw.quizwhizz.model.game.Question;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Klasa testująca poprawnosc dzialania fabryki gier.
 *
 * @author Karolina Prusaczyk
 * @see GameFactory
 */
public class GameFactoryTest {

    @Test
    public void testBuild() throws IllegalNumberOfQuestionsException {
        GameFactory gameFactory = new GameFactory();
        Category category = mock(Category.class);
        List<Question> questions = givenListOfQuestions();

        Game game = gameFactory.build(category, questions);
        Game game2 = gameFactory.build(category, questions);

        assertThat(game).isNotNull();
        assertThat(game.getCategory()).isEqualTo(category);
        assertThat(game.getQuestions()).isEqualTo(questions);
        assertThat(game).isNotEqualTo(game2);
    }

    private List<Question> givenListOfQuestions() {
        List<Question> questions = mock(List.class);
        when(questions.size()).thenReturn(10);
        return questions;
    }
}