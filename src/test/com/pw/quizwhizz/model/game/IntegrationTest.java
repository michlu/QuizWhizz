package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.exception.ScoreCannotBeRetrievedBeforeGameIsClosedException;
import com.pw.quizwhizz.model.game.*;
import com.pw.quizwhizz.service.QuestionService;
import com.pw.quizwhizz.service.exception.NoQuestionsInDBException;
import com.pw.quizwhizz.service.impl.QuestionServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Testy integracyjne symulujące przebieg gry
 *
 * @author Karolina Prusaczyk
 */
public class IntegrationTest {

    @Test
    public void GivenSeveralPlayers_WhenEveryoneSubmitsTheirAnswers_ThenWinnerIsDeterminedAndGivenBonus() throws IllegalNumberOfQuestionsException, IllegalTimeOfAnswerSubmissionException, ScoreCannotBeRetrievedBeforeGameIsClosedException, NoQuestionsInDBException {

        Category testCategory = new Category("Test");
        QuestionService service =  mock(QuestionServiceImpl.class);
        GameStateMachine gsm = mock(GameStateMachine.class);

        List<Question> questions = mock(List.class);
        when(questions.size()).thenReturn(10);
        when(service.getRandomQuestionsByCategory(testCategory, 10)).thenReturn(questions);

        Game game = new Game(testCategory, questions, gsm);
        Player playerOne = new Player("Player 1", game);

        Player playerTwo = new Player("Player 2", game);
        Player playerThree = new Player("Player 3", game);

        playerOne.startGame();

        playerOne.submitAnswers(getCorrectAnswers(5));
        playerTwo.submitAnswers(getCorrectAnswers(6));
        playerThree.submitAnswers(getCorrectAnswers(3));

        when(gsm.gameIsClosed()).thenReturn(true);
        List<Score> scores = game.checkScores();

        Player actualWinner = scores.stream()
                .sorted(Comparator.comparingInt(Score::getPoints).reversed())
                .findFirst()
                .get()
                .getPlayer();

        assertThat(playerOne.isOwner()).isTrue();
        assertThat(playerTwo.isOwner()).isFalse();
        assertThat(playerThree.isOwner()).isFalse();
        assertThat(actualWinner).isEqualTo(playerTwo);
        assertThat(actualWinner.getXp()).isEqualTo(6 * 10 + 30);
    }

    private ArrayList<Answer> getCorrectAnswers(int numberOfCorrectAnswers) {
        ArrayList<Answer> answers = new ArrayList<>();

        for (int i = 0; i < numberOfCorrectAnswers; i++) {
            answers.add(new Answer("", true));
        }

        return answers;
    }
}
