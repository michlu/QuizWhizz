package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.entity.GameEntity;
import com.pw.quizwhizz.model.entity.GameStats;
import com.pw.quizwhizz.model.entity.PlayerInGameEntity;
import com.pw.quizwhizz.model.entity.QuestionInGameEntity;

import java.util.HashMap;


public interface GameService {
    HashMap<Long, Game> findAllGames();
    void addGame(Game game);
    Game findGameById(Long gameId);
    void deleteGameById(Long gameId);
    void updateGame(Long gameId, Game game);

    GameEntity findGameEntityByGameId(Long gameId);
    PlayerInGameEntity findPlayerInGameEntityByGameId(Long gameId);
    QuestionInGameEntity findQuestionInGameEntityByGameId(Long gameId);
    GameStats findGameStatsByGameId(Long gameId);
}
