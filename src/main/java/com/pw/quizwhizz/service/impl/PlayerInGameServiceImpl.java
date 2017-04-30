package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.player.Player;
import com.pw.quizwhizz.model.PlayerInGame;
import com.pw.quizwhizz.repository.PlayerInGameRepository;
import com.pw.quizwhizz.service.PlayerInGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Karolina on 30.04.2017.
 */
@Service
public class PlayerInGameServiceImpl implements PlayerInGameService {
    private final PlayerInGameRepository repository;

    @Autowired
    public PlayerInGameServiceImpl(PlayerInGameRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlayerInGame convertToPlayerInGame(Player player, Game game) {
        PlayerInGame playerInGame = new PlayerInGame(player.getName(), game);
        return playerInGame;
    }
}
