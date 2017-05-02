package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.PlayerInGame;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.GameDTO;
import com.pw.quizwhizz.model.game.GameStats;
import com.pw.quizwhizz.model.player.Player;
import com.pw.quizwhizz.model.player.PlayerInGameDTO;
import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.question.QuestionInGameDTO;

import java.util.List;

/**
 * Created by Karolina on 02.05.2017.
 */
public interface GameService {

    List<GameDTO> findAll();
    GameDTO findById(Long id);
    Game findGameById(Long id) throws IllegalNumberOfQuestionsException;
    Game createGame(List<Question> questions) throws IllegalNumberOfQuestionsException;
    void saveGame(Game game);
    void deleteGameById(Long gameId);
    void startGame(PlayerInGame playerInGame);

    GameStats findGameStatsByGameId(Long gameId);

    PlayerInGame getNewPlayerInGame(User user, Game game);
    PlayerInGame findPlayerInGameByUserAndGame(User user, Game game);
    List<PlayerInGameDTO> findAllPlayersInGameByGameId(Long gameId);
    void savePlayerInGame(PlayerInGame playerInGame);
    void deletePlayerInGame(PlayerInGame playerInGame);

    List<QuestionInGameDTO> findAllQuestionsInGame();
    List <QuestionInGameDTO> findQuestionsInGameByGameId(Long gameId);
    void saveQuestionsInGame(List<Question> questions, Long gameId);
    List<Question> convertToQuestions(List<QuestionInGameDTO> questionsInGame);
    void deleteQuestionsInGameByGameId(Long gameId);
}
