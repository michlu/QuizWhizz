package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.dto.game.GameDTO;
import com.pw.quizwhizz.dto.GameStatsDTO;
import com.pw.quizwhizz.dto.game.PlayerInGameDTO;
import com.pw.quizwhizz.model.game.Question;
import com.pw.quizwhizz.dto.game.QuestionInGameDTO;

import java.util.List;

/**
 * Created by Karolina on 02.05.2017.
 */
public interface GameService {
    Game createGame(List<Question> questions) throws IllegalNumberOfQuestionsException;
    void addOwnerToGame(Game game, User user);

    List<GameDTO> findAll();
    GameDTO findById(Long id);
    Game findGameById(Long id) throws IllegalNumberOfQuestionsException;
}
