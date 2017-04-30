package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.entity.*;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;

import java.util.HashMap;
import java.util.List;


public interface GameService {
    //HashMap<Long, Game> findAllGames();
    // void addGame(Game game);
    // void updateGame(Long gameId, Game game);

    List<GameDTO> findAll();
    Game createGameWithId(Category category, List<Question> questions) throws IllegalNumberOfQuestionsException;
    void deleteGameById(Long gameId);
   // void saveQuestionsInGame(List<QuestionInGameDTO> questions);
    GameDTO findGameById(Long id);

    PlayerInGameDTO findPlayerInGameByGameId(Long gameId);
  //  QuestionInGameDTO findQuestionInGameByGameId(Long gameId);
    GameStats findGameStatsByGameId(Long gameId);

    //List<QuestionInGameDTO> convertToQuestionsInGame(List<Question> questions, Long gameId);


}
