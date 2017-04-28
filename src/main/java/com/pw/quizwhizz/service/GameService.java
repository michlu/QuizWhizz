package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.entity.*;

import java.util.HashMap;
import java.util.List;


public interface GameService {
    HashMap<Long, Game> findAllGames();
    void addGame(Game game);
    Game findGameById(Long gameId);
    void deleteGameById(Long gameId);
    void updateGame(Long gameId, Game game);
    void saveQuestionsInGame(List<QuestionInGameEntity> questions);

    GameEntity findGameEntityByGameId(Long gameId);
    PlayerInGameEntity findPlayerInGameEntityByGameId(Long gameId);
    QuestionInGameEntity findQuestionInGameEntityByGameId(Long gameId);
    GameStats findGameStatsByGameId(Long gameId);

    List<QuestionInGameEntity> convertToQuestionsInGame(List<Question> questions, Long gameId);


}
