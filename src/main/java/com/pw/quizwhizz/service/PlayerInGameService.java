package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.player.Player;
import com.pw.quizwhizz.model.PlayerInGame;

/**
 * Created by Karolina on 30.04.2017.
 */
public interface PlayerInGameService {
    PlayerInGame convertToPlayerInGame(Player player, Game game);
}
