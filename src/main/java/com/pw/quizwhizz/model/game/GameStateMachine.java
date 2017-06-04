package com.pw.quizwhizz.model.game;

import lombok.Getter;
import lombok.Setter;
import java.time.Clock;
import java.time.Instant;

/**
 * Maszyna stanu gry odpowiedzialna za zapewnienie poprawnego przechodzenia z jednego stanu gry w kolejny.
 * Dostarcza informacji na temat czasu rozpoczęcia gry oraz jej akualnego stanu.
 *
 * @author Karolina Prusaczyk
 */

public class GameStateMachine {
    Clock clock;
    static final int answerTimeForSingleQuestionInSeconds = 15;
    static final int timeFrameForAnswerSubmissionInSeconds = 10;
    int timeUntilAnswerEvaluationInSeconds;
    int timeUntilGameClosureInSeconds;
    @Getter @Setter
    GameState currentState;
    @Getter @Setter
    Instant startTime;

    protected GameStateMachine(int appropriateNumberOfQuestions, Clock clock) {
        this.timeUntilAnswerEvaluationInSeconds = appropriateNumberOfQuestions * answerTimeForSingleQuestionInSeconds;
        this.timeUntilGameClosureInSeconds = timeUntilAnswerEvaluationInSeconds + timeFrameForAnswerSubmissionInSeconds;
        this.currentState = GameState.OPEN;
        this.clock = clock;
    }

    public void determineCurrentState() {
        Instant now = clock.instant();

        if (startTime.plusSeconds(timeUntilGameClosureInSeconds).isBefore(now)) {
            currentState = GameState.CLOSED;
        } else if (startTime.plusSeconds(timeUntilAnswerEvaluationInSeconds).isBefore(now) && startTime.plusSeconds(timeUntilGameClosureInSeconds).isAfter(now)) {
            currentState = GameState.EVALUATING_ANSWERS;
        }
    }

    protected void start() {
        currentState = GameState.STARTED;
        startTime = clock.instant();
    }

    protected boolean gameIsClosed() {
        return currentState == GameState.CLOSED;
    }

    protected boolean gameIsNotInProgress() {
        return currentState != GameState.STARTED && currentState != GameState.EVALUATING_ANSWERS;
    }
}