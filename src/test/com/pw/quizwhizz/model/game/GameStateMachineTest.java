package com.pw.quizwhizz.model.game;

import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Klasa testująca poprawnosc dzialania maszyny stanow gry.
 *
 * @author Karolina Prusaczyk
 * @see GameStateMachine
 */
public class GameStateMachineTest {
    private Clock mockClock;
    GameStateMachine stateMachine;
    static final int timeFrameForAnswerSubmissionInSeconds = 10;
    int timeUntilAnswerEvaluationInSeconds = 150;
    int timeUntilGameClosureInSeconds = timeUntilAnswerEvaluationInSeconds + timeFrameForAnswerSubmissionInSeconds;

    /**
     * Inicjalizacja obiektu mockujacego zegar oraz instancji GameStateMachine.
     */
    @Before
    public void setUp() {
        mockClock = mock(Clock.class);
        stateMachine = new GameStateMachine(10, mockClock);
    }

    /**
     * Test konstruktora sprawdzajacy wlasciwosci poczatkowe maszyny stanu gry.
     */
    @Test
    public void constructorTest() {
        GameStateMachine gsm = new GameStateMachine(10, mockClock);

        assertThat(gsm.clock).isEqualTo(mockClock);
        assertThat(gsm.getCurrentState()).isEqualTo(GameState.OPEN);
        assertThat(gsm.gameIsClosed()).isFalse();
        assertThat(gsm.gameIsNotInProgress()).isTrue();
    }

    /**
     * Test potwierdzajacy, ze wywolanie metody start skutkuje ustawieniem daty rozpoczecia gry
     * oraz przestawieniem stanu z open na started.
     */
    @Test
    public void givenClock_WhenStartCalled_ThenGameStateShouldBeStartedAndStartedDateShouldBeDetermined() {
        stateMachine.start();

        assertThat(stateMachine.currentState).isEqualTo(GameState.STARTED);
        assertThat(stateMachine.gameIsClosed()).isFalse();
        assertThat(stateMachine.gameIsNotInProgress()).isFalse();
        verify(mockClock, times(1)).instant();
    }

    /**
     * Test weryfikujacy ustawienie stanu gry na closed po uplywie ustalonego czasu od momentu rozpoczęcia gry.
     */
    @Test
    public void givenTimeToCloseGameHasPassed_WhenDetermineCurrentStateIsCalled_ThenStateShouldBeClosed() {
        givenStateMachineStartedAndSetStartTime();
        givenTimeToCloseGameHasPassed();

        stateMachine.determineCurrentState();

        assertThat(stateMachine.getCurrentState()).isEqualTo(GameState.CLOSED);
        assertThat(stateMachine.gameIsClosed()).isTrue();
        assertThat(stateMachine.gameIsNotInProgress()).isTrue();
    }

    /**
     * Test potwierdzajacy, ze po uplynięciu czasu na skladanie odpowiedzi, czyli po wyjsciu gry ze stanu started
     * i przed jej wejsciem w stan zamknięty, gra znajduje się w stanie evaluating_answers.
     */
    @Test
    public void givenNowIsAfterEvaluationTimeAndBeforeGameClosure_WhenDetermineCurrentStateIsCalled_ThenStateShouldBeEvaluatingAnswers() {
        givenStateMachineStartedAndSetStartTime();
        givenEvaluationTimeBeforeGameClosure();

        stateMachine.determineCurrentState();

        assertThat(stateMachine.getCurrentState()).isEqualTo(GameState.EVALUATING_ANSWERS);
    }

    /**
     * Test weryfikujacy stan gry bezposrednio przed jej zamknięciem.
     */
    @Test
    public void givenItIsJustBeforeGameClosure_WhenDetermineCurrentStateIsCalled_ThenStateShouldBeEvaluatingAnswers() {
        givenStateMachineStartedAndSetStartTime();
        givenTimeJustBeforeGameClosure();

        stateMachine.determineCurrentState();

        assertThat(stateMachine.getCurrentState()).isEqualTo(GameState.EVALUATING_ANSWERS);
        assertThat(stateMachine.gameIsClosed()).isFalse();
        assertThat(stateMachine.gameIsNotInProgress()).isFalse();
    }

    /**
     * Test weryfikujacy poprawnosc stanu gry bezposrednio po rozpoczęciu czasu przewidzianego dla stanu evaluating_answers.
     */
    @Test
    public void givenItIsJustAfterEvaluationTime_WhenDetermineCurrentStateIsCalled_ThenStateShouldBeEvaluatingAnswers() {
        givenStateMachineStartedAndSetStartTime();
        givenEvaluationTimeJustStarted();

        stateMachine.determineCurrentState();

        assertThat(stateMachine.getCurrentState()).isEqualTo(GameState.EVALUATING_ANSWERS);
    }

    /**
     * Test weryfikujacy poprawnosc stanu gry bezposrednio przed uplynięciem czasu przewidzianego dla stanu started.
     */
    @Test
    public void givenItIsJustBeforeEvaluationTime_WhenDetermineCurrentStateIsCalled_ThenStateShouldBeStarted() {
        givenStateMachineStartedAndSetStartTime();
        givenTimeJustBeforeEvaluation();

        stateMachine.determineCurrentState();

        assertThat(stateMachine.getCurrentState()).isEqualTo(GameState.STARTED);
        assertThat(stateMachine.getStartTime()).isNotNull();
    }

    private void givenStateMachineStartedAndSetStartTime() {
        when(mockClock.instant()).thenReturn(Instant.ofEpochSecond(0));
        stateMachine.start();
    }

    private void givenTimeToCloseGameHasPassed() {
        when(mockClock.instant()).thenReturn(Instant.ofEpochSecond(timeUntilGameClosureInSeconds + 1));
    }

    private void givenTimeJustBeforeGameClosure() {
        when(mockClock.instant()).thenReturn(Instant.ofEpochSecond(timeUntilGameClosureInSeconds - 1));
    }

    private void givenEvaluationTimeBeforeGameClosure() {
        when(mockClock.instant()).thenReturn(Instant.ofEpochSecond(timeUntilAnswerEvaluationInSeconds + 5));
    }

    private OngoingStubbing<Instant> givenEvaluationTimeJustStarted() {
        return when(mockClock.instant()).thenReturn(Instant.ofEpochSecond(timeUntilAnswerEvaluationInSeconds + 1));
    }

    private void givenTimeJustBeforeEvaluation() {
        when(mockClock.instant()).thenReturn(Instant.ofEpochSecond(timeUntilAnswerEvaluationInSeconds - 1));
    }
}
