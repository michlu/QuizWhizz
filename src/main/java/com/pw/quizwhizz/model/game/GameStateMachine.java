package com.pw.quizwhizz.model.game;

import lombok.Getter;
import lombok.Setter;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

/**
 * Maszyna stanu gry odpowiedzialna za zapewnienie poprawnego przechodzenia z jednego stanu gry w kolejny.
 * Dostarcza informacji na temat czasu rozpoczęcia gry oraz jej akualnego stanu.
 *
 * @author Karolina Prusaczyk
 */

public class GameStateMachine {
    /**
     * Instancja obiektu klasy Clock do obslugi czasu w grze.
     */
    Clock clock;
    /**
     * Czas na odpowiedz dla pojedynczego pytania w sekundach.
     */
    static final int answerTimeForSingleQuestionInSeconds = 15;
    /**
     * Dodatkowy czas w sekundach na wyslanie wybranych odpowiedzi po uplywie czasu przeznaczonego na wszystkie pytania.
     * Stanowi zabezpieczenie w przypadku wolno dzialajacych przegladarek.
     */
    static final int timeFrameForAnswerSubmissionInSeconds = 10;
    /**
     * Czas, po ktorym gra wejdzie w stan oceniania odpowiedzi, podany w sekundach.
     */
    int timeUntilAnswerEvaluationInSeconds;
    /**
     * Czas, po ktorym gra wejdzie w stan zamknięty, podany w sekundach.
     */
    int timeUntilGameClosureInSeconds;
    /**
     * Obecny stan gry.
     */
    @Getter
    @Setter
    GameState currentState;
    /**
     * Data rozpoczęcia gry.
     */
    @Getter
    @Setter
    Instant startTime;

    /**
     * Konstruktor przyjmujacy
     *
     * @param appropriateNumberOfQuestions liczbę pytań w grze do obliczenia czasu trwania gry
     * @param clock                        instancję zegara do obslugi czasu
     */

    protected GameStateMachine(int appropriateNumberOfQuestions, Clock clock) {
        this.timeUntilAnswerEvaluationInSeconds = appropriateNumberOfQuestions * answerTimeForSingleQuestionInSeconds;
        this.timeUntilGameClosureInSeconds = timeUntilAnswerEvaluationInSeconds + timeFrameForAnswerSubmissionInSeconds;
        this.currentState = GameState.OPEN;
        this.clock = clock;
    }

    /**
     * Metoda wywolywana dla trwajacej lub zakonczonej juz gry, w celu ustalenia, czy jej obecny stan pozwala na
     * wyslanie odpowiedzi lub uzyskanie wynikow.
     *
     * @see Game#checkScores()
     * @see Game#evaluateAnswers(Player, List)
     */
    public void determineCurrentState() {
        Instant now = clock.instant();

        if (startTime.plusSeconds(timeUntilGameClosureInSeconds).isBefore(now)) {
            currentState = GameState.CLOSED;
        } else if (startTime.plusSeconds(timeUntilAnswerEvaluationInSeconds).isBefore(now) && startTime.plusSeconds(timeUntilGameClosureInSeconds).isAfter(now)) {
            currentState = GameState.EVALUATING_ANSWERS;
        }
    }

    /**
     * Metoda wykorzystywana do ustawienia stanu gry na rozpoczęty oraz okreslenia czasu jej rozpoczęcia.
     *
     * @see Game#start()
     */
    protected void start() {
        currentState = GameState.STARTED;
        startTime = clock.instant();
    }

    /**
     * Metoda sprawdzajaca, czy gra się skończyla
     *
     * @return wartosc true lub false
     */
    protected boolean gameIsClosed() {
        return currentState == GameState.CLOSED;
    }

    /**
     * Metoda sprawdzajaca, czy gra jest w stanie innym niz rozpoczęta lub oceniajaca odpowiedzi.
     *
     * @return wartosc true lub false
     * @see Game#evaluateAnswers(Player, List)
     */
    protected boolean gameIsNotInProgress() {
        return currentState != GameState.STARTED && currentState != GameState.EVALUATING_ANSWERS;
    }
}