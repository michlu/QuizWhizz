package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.player.Player;
import com.pw.quizwhizz.model.PlayerInGame;
import com.pw.quizwhizz.model.player.PlayerInGameDTO;

/**
 * Created by Karolina on 30.04.2017.
 */
public interface PlayerInGameService {
    PlayerInGame convertToPlayerInGame(Player player, Game game);
    PlayerInGameDTO convertToPlayerInGameDTO(PlayerInGame playerInGame);
    void savePlayerInGameDTO(PlayerInGameDTO playerInGameDTO);
}
