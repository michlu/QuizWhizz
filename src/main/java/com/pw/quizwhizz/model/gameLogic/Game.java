package com.pw.quizwhizz.model.gameLogic;

import com.pw.quizwhizz.model.gameLogic.exceptions.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.gameLogic.exceptions.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.gameLogic.exceptions.ScoreCannotBeRetrievedBeforeGameIsClosedException;
import com.pw.quizwhizz.model.gameLogic.entity.Answer;
import com.pw.quizwhizz.model.gameLogic.entity.Category;
import com.pw.quizwhizz.model.gameLogic.entity.Question;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import javax.persistence.*;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Karolina on 24.03.2017.
 */

public class Game {
    static final int appropriateNumberOfQuestions = 10;
    @Getter
    private final Category category;
    @Getter
    private final List<Question> questions;
    private final GameStateMachine gameStateMachine;
    @Getter
    private List<Player> players = new ArrayList<>();
    private List<Score> scores = new ArrayList<>();

    // Production code constructor
    public Game(Category category, List<Question> questions) throws IllegalNumberOfQuestionsException {
        validateNumberOfQuestions(questions);

        this.category = category;
        this.questions = questions;
        this.gameStateMachine = new GameStateMachine(appropriateNumberOfQuestions, Clock.systemUTC());
    }

    // Constructor for testing purposes (a solution to time-related issues)
    protected Game(Category category, List<Question> questions, GameStateMachine gameStateMachine) throws IllegalNumberOfQuestionsException {
        validateNumberOfQuestions(questions);

        this.category = category;
        this.questions = questions;
        this.gameStateMachine = gameStateMachine;
    }

    public List<Score> getScores() throws ScoreCannotBeRetrievedBeforeGameIsClosedException {
        gameStateMachine.determineCurrentState();

        if (!gameStateMachine.gameIsClosed()) {
            throw new ScoreCannotBeRetrievedBeforeGameIsClosedException();
        }

        if (winnerIsNotDetermined() && scores.size() > 0) {
            Score winningScore = findWinningScore();
            winningScore.markAsHighest();
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

        Score score = new Score(player);
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

    private Score findWinningScore() {
        return scores.stream().sorted(Comparator.comparingInt(Score::getPoints).reversed()).findFirst().get();
    }

    private boolean playersScoreIsAlreadyAddedToScores(Player player) {
        return scores.stream().filter(score -> score.getPlayer().equals(player)).count() > 0;
    }

}
