package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.category.Category;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.GameDTO;
import com.pw.quizwhizz.model.game.GameStats;
import com.pw.quizwhizz.model.player.PlayerInGameDTO;
import com.pw.quizwhizz.model.question.Question;

import java.util.List;


public interface GameDTOService {
    //HashMap<Long, Game> findAllGames();
    // void addGame(Game game);
    // void updateGame(Long gameId, Game game);

    List<GameDTO> findAll();
    GameDTO convertToGameDTO(Game game);
    Game createGameWithId(Category category, List<Question> questions) throws IllegalNumberOfQuestionsException;
    void deleteGameById(Long gameId);
   // void saveQuestionsInGame(List<QuestionInGameDTO> questions);
    GameDTO findGameById(Long id);

    PlayerInGameDTO findPlayerInGameByGameId(Long gameId);
  //  QuestionInGameDTO findQuestionInGameByGameId(Long gameId);
    GameStats findGameStatsByGameId(Long gameId);

    //List<QuestionInGameDTO> convertToQuestionsInGame(List<Question> questions, Long gameId);


}
