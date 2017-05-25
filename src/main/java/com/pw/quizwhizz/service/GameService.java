package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.exception.ScoreCannotBeRetrievedBeforeGameIsClosedException;
import com.pw.quizwhizz.model.game.*;

import java.util.List;

/**
 * Created by Karolina on 02.05.2017.
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
    Score findScoreByUserAndGame(long userId, long gameId) throws IllegalNumberOfQuestionsException;
    List<Score> getScoresByGameId(long gameId) throws IllegalNumberOfQuestionsException, ScoreCannotBeRetrievedBeforeGameIsClosedException;
    List<Score> getScoresByPlayer(Player player);
    List<Game> getAllOpenGames() throws IllegalNumberOfQuestionsException;
    List<String> getNamesOfPlayersInGame(Long gameId);

    boolean isPlayerGameOwner(Long id, Long gameId);
}
