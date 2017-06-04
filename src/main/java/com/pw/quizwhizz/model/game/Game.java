package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.exception.ScoreCannotBeRetrievedBeforeGameIsClosedException;
import lombok.Getter;
import lombok.Setter;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Centralna klasa logiki biznesowej okreslająca zasady gry.
 *
 * @author Karolina Prusaczyk
 */
@Getter
public class Game {
    @Setter
    private long id;
    static final int appropriateNumberOfQuestions = 10;
    private Category category;
    private List<Question> questions;
    private final GameStateMachine gameStateMachine;
    @Setter
    private List<Player> players = new ArrayList<>();
    @Setter
    private List<Score> scores = new ArrayList<>();
    private ScoreBuilder scoreBuilder;

    /**
     * Konstruktor wykorzystywany w kodzie produkcyjnym
     *
     * @param category  kategoria gry
     * @param questions pytania w grze
     */
    public Game(Category category, List<Question> questions) throws IllegalNumberOfQuestionsException {
        validateNumberOfQuestions(questions);

        this.category = category;
        this.questions = questions;
        this.gameStateMachine = new GameStateMachine(appropriateNumberOfQuestions, Clock.systemUTC());
        this.scoreBuilder = new ScoreBuilder();
    }

    /**
     * Konstruktor tworzący lekką instancję gry z maszyną stanu wykorzystywaną w celu ustalenia obecnego stanu gry.
     */
    public Game() {
        this.gameStateMachine = new GameStateMachine(appropriateNumberOfQuestions, Clock.systemUTC());
    }

    /**
     * Konstruktor wykorzystywany w celach testowych. Umozliwia zastosowanie obiektow mockujących w testach.
     *
     * @param category         kategoria gry
     * @param questions        pytania w grze
     * @param gameStateMachine maszyna stanu gry
     */
    protected Game(Category category, List<Question> questions, GameStateMachine gameStateMachine) throws IllegalNumberOfQuestionsException {
        validateNumberOfQuestions(questions);

        this.category = category;
        this.questions = questions;
        this.gameStateMachine = gameStateMachine;
        this.scoreBuilder = new ScoreBuilder();
    }

    /**
     * Metoda, ktora nalezy wywolac, aby uzyskac ostateczne wyniki gry.
     * Po przejsciu gry w stan zamkniety i tym samym upewnieniu się, ze wszyscy uczestnicy wyslali odpowiedzi,
     * następuje wylonienie zwycięzcy lub zwycięzcow.
     * Jesli w grze wzięlo udzial co najmniej dwoch uczestnikow, zwycięzca zostaje nagrodzony dodatkowymi punktami doswiadczenia.
     *
     * @return lista wynikow danej rozgrywki
     * @throws ScoreCannotBeRetrievedBeforeGameIsClosedException jesli metoda zostanie wywolana zbyt wczesnie
     * @see Score#markAsHighest()
     */
    public List<Score> checkScores() throws ScoreCannotBeRetrievedBeforeGameIsClosedException {
        gameStateMachine.determineCurrentState();

        if (!gameStateMachine.gameIsClosed()) {
            throw new ScoreCannotBeRetrievedBeforeGameIsClosedException();
        }

        if (winnerIsNotDetermined() && scores.size() > 1) {
            List<Score> winningScores = findHighestScoreOrScores();
            winningScores.forEach(score -> score.markAsHighest());
        }
        return scores;
    }

    /**
     * Metoda dodająca gracza do listy graczy w danej grze, jesli nie zostal juz do niej dodany.
     *
     * @param player gracz dolączający do gry.
     */
    protected void addPlayer(Player player) {
        if (players.contains(player)) {
            return;
        }
        players.add(player);
    }

    /**
     * Metoda rozpoczynająca grę
     *
     * @see GameStateMachine#start()
     */
    protected void start() {
        gameStateMachine.start();
    }

    /**
     * Metoda przyjmuje gracza wraz z listą jego odpowiedzi. Po sprawdzeniu, czy gra jest w odpowiednim stanie,
     * przesyla je do instancji klasy Score, w ktorej następuje przeliczenie wyniku.
     *
     * @param player           gracz
     * @param submittedAnswers odpowiedzi gracza
     * @throws IllegalTimeOfAnswerSubmissionException jesli gracz spozni się z wyslaniem odpowiedzi
     * @see Player#submitAnswers(List)
     * @see Score#evaluateAnswers(List)
     */
    protected void evaluateAnswers(Player player, List<Answer> submittedAnswers) throws IllegalTimeOfAnswerSubmissionException {
        gameStateMachine.determineCurrentState();

        if (gameStateMachine.gameIsNotInProgress()) {
            throw new IllegalTimeOfAnswerSubmissionException();
        }

        if (playersScoreIsAlreadyAddedToScores(player)) {
            return;
        }

        Score score = scoreBuilder.withPlayer(player).build();
        score.evaluateAnswers(submittedAnswers);
        scores.add(score);
    }

    private void validateNumberOfQuestions(List<Question> questions) throws IllegalNumberOfQuestionsException {
        if (questions.size() != appropriateNumberOfQuestions) {
            throw new IllegalNumberOfQuestionsException();
        }
    }

    private boolean winnerIsNotDetermined() {
        return scores.stream().filter(score -> score.isHighest()).count() == 0;
    }

    private List<Score> findHighestScoreOrScores() {
        Score highestScore = scores.stream().sorted(Comparator.comparingInt(Score::getPoints).reversed()).findFirst().get();
        List<Score> highestScores = new ArrayList<>();
        scores.forEach(score -> {
            if (score.getPoints() == highestScore.getPoints()) {
                highestScores.add(score);
            }
        });
        return highestScores;
    }

    private boolean playersScoreIsAlreadyAddedToScores(Player player) {
        return scores.stream().filter(score -> score.getPlayer().equals(player)).count() > 0;
    }

}
