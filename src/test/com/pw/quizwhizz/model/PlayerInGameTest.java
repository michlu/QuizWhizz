package com.pw.quizwhizz.model;

import com.pw.quizwhizz.model.entity.Answer;
import com.pw.quizwhizz.model.entity.Question;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.entity.Category;
import org.junit.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created by Karolina on 18.04.2017.
 */
public class PlayerInGameTest {
    Player playerOne = new Player("Player1");
    Player playerTwo = new Player("Player2");
    Player playerThree = new Player("Player3");

    @Test
    public void givenNewPlayer_WhenCreated_ThenGetPlayersAndAddPlayerMethodsInGameShouldBeCalled() {
        Game game = mock(Game.class);

        PlayerInGame player1 = new PlayerInGame(playerOne, game);
        PlayerInGame player2 = new PlayerInGame(playerTwo, game);

        verify(game, times(2)).getPlayers();
        verify(game, times(1)).addPlayer(player1.getPlayer());
        verify(game, times(1)).addPlayer(player2.getPlayer());
    }

    @Test
    public void givenSeveralPlayersInGame_WhenCheckedForBeingGameOwner_ThenTrueShouldBeReturnedOnlyForFirstPlayer() throws IllegalNumberOfQuestionsException {
        Game gameSpy = givenGameInProgress();

        PlayerInGame player1 = new PlayerInGame(playerOne, gameSpy);
        PlayerInGame player2 = new PlayerInGame(playerTwo, gameSpy);
        PlayerInGame player3 = new PlayerInGame(playerThree, gameSpy);

        assertThat(player1.getGame()).isEqualTo(gameSpy);
        assertThat(player1.isOwner()).isTrue();
        assertThat(player2.isOwner()).isFalse();
        assertThat(player3.isOwner()).isFalse();
    }

    @Test
    public void givenSeveralPlayers_WhenStartGameCalled_ThenItShouldOnlyStartForGameOwner() throws IllegalNumberOfQuestionsException {
        Game gameSpy = givenGameInProgress();

        PlayerInGame player1 = new PlayerInGame(playerOne, gameSpy);
        PlayerInGame player2 = new PlayerInGame(playerTwo, gameSpy);
        PlayerInGame player3 = new PlayerInGame(playerThree, gameSpy);

        player1.startGame();
        player2.startGame();
        player3.startGame();

        verify(gameSpy, times(1)).start();
    }

    @Test
    public void givenAnswersAreNull_WhenSubmitAnswersCalled_ThenEvaluateAnswersInGameShouldNotBeCalled() throws IllegalNumberOfQuestionsException, IllegalTimeOfAnswerSubmissionException {
        Game gameSpy = givenGameInProgress();
        PlayerInGame player1 = new PlayerInGame(playerOne, gameSpy);

        List<Answer> answers = null;
        player1.submitAnswers(answers);

        verify(gameSpy, never()).evaluateAnswers(player1.getPlayer(), answers);
    }

    @Test
    public void givenAnswersAreNotNull_WhenSubmitAnswersCalled_ThenEvaluateAnswersInGameShouldBeCalled() throws IllegalNumberOfQuestionsException, IllegalTimeOfAnswerSubmissionException {
        Game gameSpy = givenGameInProgress();
        List<Answer> answers = givenListOfAnswers();
        PlayerInGame player1 = new PlayerInGame(playerOne, gameSpy);

        player1.submitAnswers(answers);

        verify(gameSpy, times(1)).evaluateAnswers(player1.getPlayer(), answers);
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
