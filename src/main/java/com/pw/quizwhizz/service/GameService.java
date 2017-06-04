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
    List<Game> getAllOpenGames() throws IllegalNumberOfQuestionsException;
    Game findGameById(Long id) throws IllegalNumberOfQuestionsException;
    Game createGame(List<Question> questions) throws IllegalNumberOfQuestionsException;
    void addOwnerToGame(Game game, User user);
    void addPlayerToGame(Game game, User user);
    boolean isPlayerGameOwner(Long id, Long gameId);
    List<String> getNamesOfPlayersInGame(Long gameId);
    boolean isGameStarted(Long gameId);
    void startGame(Game game, User user) throws IllegalNumberOfQuestionsException;
    void submitAnswers(Game game, User user, List<Long> answerIds) throws IllegalTimeOfAnswerSubmissionException, IllegalNumberOfQuestionsException;
    void saveScore(Score score);
    boolean isGameClosed(Long gameId);
    List<Score> checkScores(long gameId) throws IllegalNumberOfQuestionsException, ScoreCannotBeRetrievedBeforeGameIsClosedException;

}
