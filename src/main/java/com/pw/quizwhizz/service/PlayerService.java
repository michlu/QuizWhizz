package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Player;

public interface PlayerService {
    void updateAsDTO(Player player);
    Player findByIdAndGame(Long id, Game game);
}
