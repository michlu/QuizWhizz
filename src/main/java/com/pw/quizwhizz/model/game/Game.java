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
 * Centralna klasa logiki biznesowej okreslajaca zasady gry
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
    private ScoreBuilder scoreBuilder = new ScoreBuilder();

    // Production code constructor
    public Game(Category category, List<Question> questions) throws IllegalNumberOfQuestionsException {
        validateNumberOfQuestions(questions);

        this.category = category;
        this.questions = questions;
        this.gameStateMachine = new GameStateMachine(appropriateNumberOfQuestions, Clock.systemUTC());
    }

    // Constructor for pulling purposes
    public Game() {
        this.gameStateMachine = new GameStateMachine(appropriateNumberOfQuestions, Clock.systemUTC());
    }
    // Constructor for testing purposes (a solution to time-related issues)
    protected Game(Category category, List<Question> questions, GameStateMachine gameStateMachine) throws IllegalNumberOfQuestionsException {
        validateNumberOfQuestions(questions);

        this.category = category;
        this.questions = questions;
        this.gameStateMachine = gameStateMachine;
    }

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

    protected void addPlayer(Player player) {
        if (players.contains(player)) {
            return;
        }
        players.add(player);
    }

    protected void start() {
        gameStateMachine.start();
    }

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
        Score highestScore =  scores.stream().sorted(Comparator.comparingInt(Score::getPoints).reversed()).findFirst().get();
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
