package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.game.*;
import org.junit.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testy weryfikujące poprawnosc dzialania klasy Player
 *
 * @author Karolina Prusaczyk
 * @see Player
 */
public class PlayerTest {

    @Test
    public void givenNewPlayer_WhenCreated_ThenGetPlayersAndAddPlayerMethodsInGameShouldBeCalled() {
        Game game = mock(Game.class);

        Player player1 = new Player("Aleksander", game);
        Player player2 = new Player("Aleksandra", game);

        verify(game, times(2)).getPlayers();
        verify(game, times(1)).addPlayer(player1);
        verify(game, times(1)).addPlayer(player2);
    }

    @Test
    public void givenSeveralPlayersInGame_WhenCheckedForBeingGameOwner_ThenTrueShouldBeReturnedOnlyForFirstPlayer() throws IllegalNumberOfQuestionsException {
        Game gameSpy = givenGameInProgress();

        Player player1 = new Player("Karol", gameSpy);
        Player player2 = new Player("Konrad", gameSpy);
        Player player3 = new Player("Krystyna", gameSpy);

        assertThat(player1.getGame()).isEqualTo(gameSpy);
        assertThat(player1.isOwner()).isTrue();
        assertThat(player2.isOwner()).isFalse();
        assertThat(player3.isOwner()).isFalse();
    }

    @Test
    public void givenSeveralPlayers_WhenStartGameCalled_ThenItShouldOnlyStartForGameOwner() throws IllegalNumberOfQuestionsException {
        Game gameSpy = givenGameInProgress();

        Player player1 = new Player("Marianna", gameSpy);
        Player player2 = new Player("Maurycy", gameSpy);
        Player player3 = new Player("Malwina", gameSpy);

        player1.startGame();
        player2.startGame();
        player3.startGame();

        verify(gameSpy, times(1)).start();
    }

    @Test
    public void givenAnswersAreNull_WhenSubmitAnswersCalled_ThenEvaluateAnswersInGameShouldNotBeCalled() throws IllegalNumberOfQuestionsException, IllegalTimeOfAnswerSubmissionException {
        Game gameSpy = givenGameInProgress();
        Player player1 = new Player("Piotr", gameSpy);

        List<Answer> answers = null;
        player1.submitAnswers(answers);

        verify(gameSpy, never()).evaluateAnswers(player1, answers);
    }

    @Test
    public void testEquals() {
        Player player1 = new Player("Janek Jankowski");
        Player player2 = new Player("Janek Jankowski");
        Player player3 = new Player("Janek Jankowski");

        player1.incrementGamesPlayed();
        player2.incrementGamesPlayed();

        player1.addXp(20);
        player2.addXp(20);

        assertThat(player1.equals(player2));
        assertThat(!player3.equals(player1));
    }

    @Test
    public void testHashCode() throws Exception {
        Player player1 = new Player("Joanna Janicka");
        Player player2 = new Player("Joanna Janicka");
        Player player3 = new Player("Joanna Janicka");

        player1.addXp(100);
        player2.addXp(100);

        player1.incrementGamesPlayed();
        player2.incrementGamesPlayed();

        assertThat(player1.hashCode()).isEqualTo(player2.hashCode());
        assertThat(player3.hashCode()).isNotEqualTo(player1.hashCode());
    }

    @Test
    public void testGettersAddXpAndIncrementGamesPlayedMethods() {
        Player player1 = new Player("Jerzy Jarzyna");

        player1.incrementGamesPlayed();
        player1.incrementGamesPlayed();

        player1.addXp(200);
        player1.addXp(20);

        assertThat(player1.getName()).isEqualTo("Jerzy Jarzyna");
        assertThat(player1.getGamesPlayed()).isEqualTo(2);
        assertThat(player1.getXp()).isEqualTo(220);
    }

    @Test
    public void givenAnswersAreNotNull_WhenSubmitAnswersCalled_ThenEvaluateAnswersInGameShouldBeCalled() throws IllegalNumberOfQuestionsException, IllegalTimeOfAnswerSubmissionException {
        Game gameSpy = givenGameInProgress();
        List<Answer> answers = givenListOfAnswers();
        Player player1 = new Player("Piotr", gameSpy);

        player1.submitAnswers(answers);

        verify(gameSpy, times(1)).evaluateAnswers(player1, answers);
    }

    private Game givenGameInProgress() throws IllegalNumberOfQuestionsException {
        Category category = mock(Category.class);
        List<Question> questions = mock(List.class);
        GameStateMachine stateMachine = mock(GameStateMachine.class);
        when(questions.size()).thenReturn(10);
        Game game = new Game(category, questions, stateMachine);
        Game gameSpy = spy(game);

        when(stateMachine.gameIsNotInProgress()).thenReturn(false);
        return gameSpy;
    }

    private List<Answer> givenListOfAnswers() {
        List<Answer> answers = mock(List.class);
        Answer correctAnswer = new Answer("", true);
        when(answers.get(0)).thenReturn(correctAnswer);
        return answers;
    }
}
