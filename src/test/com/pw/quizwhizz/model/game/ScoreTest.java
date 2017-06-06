package com.pw.quizwhizz.model.game;

import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Testy klasy Score odpowiedzialnej za ocenianie odpowiedzi graczy, oznaczanie najwyzszych wynikow,
 * przyznawanie punktow za grę oraz dodatkowych punktow za zwycięstwo w grze wieloosobowej.
 *
 * @author Karolina Prusaczyk
 * @see Score
 */
public class ScoreTest {
    Player player;
    Score score;

    /**
     * Test weryfikujacy dzialanie konstruktora i poczatkowe wartosci przypisane instancji klasy Score.
     */
    @Test
    public void constructorTest() {
        givenPlayer();
        givenScore();

        assertThat(score.getPlayer()).isEqualTo(player);
        assertThat(score.getPoints()).isEqualTo(0);
        assertThat(score.isHighest()).isFalse();
    }

    /**
     * Test weryfikujacy dzialanie metody markAsHighest(), ktorej pozadanym wynikiem jest oznaczenie wyniku jako najwyzszy
     * oraz nagrodzenie gracza z najwyzszym wynikiem 30 dodatkowymi punktami doswiadczenia.
     */
    @Test
    public void givenScore_WhenMarkAsHighestIsCalled_ThenScoreShouldBeHighestAndPlayerShouldGet30Xp() {
        givenPlayer();
        givenScore();

        score.markAsHighest();

        assertThat(score.isHighest()).isTrue();
        verify(player, times(1)).addXp(30);
    }

    /**
     * Test potwierdzajacy, ze gracz, ktory udzielil jedynie niepoprawnych odpowiedzi nie uzyska punktow doswiadczenia,
     * jednak liczba zagranych przez niego gier wzrosnie.
     */
    @Test
    public void givenListWithNoCorrectAnswers_WhenEvaluateAnswersIsCalled_ThenPlayerShouldReceiveNoPoints() {
        givenPlayer();
        givenScore();
        List<Answer> answers = givenListOf1IncorrectAnswer();

        score.evaluateAnswers(answers);

        verify(player, times(1)).addXp(0);
        verify(player, times(1)).incrementGamesPlayed();
    }

    /**
     * Test potwierdzajacy, ze za jedna poprawna odpowiedz gracz otrzymuje 10 punktow.
     */
    @Test
    public void givenListWith1CorrectAnswer_WhenEvaluateAnswersIsCalled_ThenPlayerShouldReceive10Points() {
        givenPlayer();
        givenScore();
        List<Answer> answers = givenListOf1CorrectAnd1IncorrectAnswers();

        score.evaluateAnswers(answers);

        verify(player, times(1)).addXp(10);
        verify(player, times(1)).incrementGamesPlayed();
    }

    /**
     * Test weryfikujacy liczbę przyznanych punktow przy kilku poprawnych odpowiedziach.
     */
    @Test
    public void givenListOfSeveralCorrectAnswers_WhenEvaluateAnswersIsCalled_ThenPlayerShouldReceiveAppropriateNumberOfPoints() {
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
        when(correctAnswer.getIsCorrect()).thenReturn(true);
        return correctAnswer;
    }

    private Answer incorrectAnswer() {
        Answer incorrectAnswer = mock(Answer.class);
        when(incorrectAnswer.getIsCorrect()).thenReturn(false);
        return incorrectAnswer;
    }
}
