package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.exception.ScoreCannotBeRetrievedBeforeGameIsClosedException;
import com.pw.quizwhizz.model.game.*;

import java.util.List;

/**
 * Abstrakcyjna warstwa serwisu
 * @author Karolina Prusaczyk
 */
public interface GameService {
    Game createGame(List<Question> questions) throws IllegalNumberOfQuestionsException;
    void addOwnerToGame(Game game, User user);
    void addPlayerToGame(Game game, User user);

    boolean isGameStarted(Long gameId);

    void startGame(Game game, User user) throws IllegalNumberOfQuestionsException;
    void submitAnswers(Game game, User user, List<Long> answerIds) throws IllegalTimeOfAnswerSubmissionException, IllegalNumberOfQuestionsException;
    Game findGameById(Long id) throws IllegalNumberOfQuestionsException;

    void saveScore(Score score);
    List<Score> checkScores(long gameId) throws IllegalNumberOfQuestionsException, ScoreCannotBeRetrievedBeforeGameIsClosedException;
    List<Game> getAllOpenGames() throws IllegalNumberOfQuestionsException;
    List<String> getNamesOfPlayersInGame(Long gameId);

    boolean isPlayerGameOwner(Long id, Long gameId);
    boolean isGameClosed(Long gameId);
}
