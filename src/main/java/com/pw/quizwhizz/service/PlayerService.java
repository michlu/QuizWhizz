package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Player;

public interface PlayerService {
    void updateEntity(Player player);
    Player findByIdAndGame(Long id, Game game) throws IllegalNumberOfQuestionsException;
}
