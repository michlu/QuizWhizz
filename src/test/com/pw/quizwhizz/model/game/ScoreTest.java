package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.game.Answer;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.model.game.Score;
import org.testng.annotations.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Karolina on 21.04.2017.
 */
public class ScoreTest {
    Player player;
    Score score;

    @Test
    public void constructorTest() {
        givenPlayer();
        givenScore();

        assertThat(score.getPlayer()).isEqualTo(player);
        assertThat(score.getPoints()).isEqualTo(0);
        assertThat(score.isHighest()).isFalse();
    }

    @Test
    public void givenScore_WhenMarkAsHighestIsCalled_ThenScoreShouldBeHighestAndPlayerShouldGet30Xp() {
        givenPlayer();
        givenScore();

        score.markAsHighest();

        assertThat(score.isHighest()).isTrue();
        verify(player, times(1)).addXp(30);
    }

    @Test
    public void givenListWithNoCorrectAnswers_WhenEvaluateAnswersIsCalled_ThenPlayerShouldReceiveNoPoints() {
        givenPlayer();
        givenScore();
        List<Answer> answers = givenListOf1IncorrectAnswer();

        score.evaluateAnswers(answers);

        verify(player, times(1)).addXp(0);
        verify(player, times(1)).incrementGamesPlayed();
    }

    @Test
    public void givenListWith1CorrectAnswer_WhenEvaluateAnswersIsCalled_ThenPlayerShouldReceive10Points() {
        givenPlayer();
        givenScore();
        List<Answer> answers = givenListOf1CorrectAnd1IncorrectAnswers();

        score.evaluateAnswers(answers);

        verify(player, times(1)).addXp(10);
        verify(player, times(1)).incrementGamesPlayed();
    }

    @Test
    public void givenListSeveralNoCorrectAnswers_WhenEvaluateAnswersIsCalled_ThenPlayerShouldReceiveAppropriateNumberOfPoints() {
        givenPlayer();
        givenScore();
        List<Answer> answers = givenListOf3CorrectAnswers();

        score.evaluateAnswers(answers);

        verify(player, times(1)).addXp(30);
        verify(player, times(1)).incrementGamesPlayed();
    }

    private void givenScore() {
        score = new Score(player);
    }

    private void givenPlayer() {
        player = mock(Player.class);
    }

    private List<Answer> givenListOf1IncorrectAnswer() {
        Answer incorrectAnswer = incorrectAnswer();
        List<Answer> answers = mock(List.class);

        when(answers.size()).thenReturn(1);
        when(answers.get(0)).thenReturn(incorrectAnswer);
        return answers;
    }

    private List<Answer> givenListOf1CorrectAnd1IncorrectAnswers() {
        Answer correctAnswer = correctAnswer();
        Answer incorrectAnswer = incorrectAnswer();
        List<Answer> answers = mock(List.class);

        when(answers.size()).thenReturn(2);
        when(answers.get(0)).thenReturn(incorrectAnswer);
        when(answers.get(1)).thenReturn(correctAnswer);
        return answers;
    }

    private List<Answer> givenListOf3CorrectAnswers() {
        Answer correctAnswer = correctAnswer();
        List<Answer> answers = mock(List.class);

        when(answers.size()).thenReturn(3);
        when(answers.get(0)).thenReturn(correctAnswer);
        when(answers.get(1)).thenReturn(correctAnswer);
        when(answers.get(2)).thenReturn(correctAnswer);
        return answers;
    }

    private Answer correctAnswer() {
        Answer correctAnswer = mock(Answer.class);
        when(correctAnswer.isCorrect()).thenReturn(true);
        return correctAnswer;
    }

    private Answer incorrectAnswer() {
        Answer incorrectAnswer = mock(Answer.class);
        when(incorrectAnswer.isCorrect()).thenReturn(false);
        return incorrectAnswer;
    }
}
