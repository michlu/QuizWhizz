package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.entity.game.PlayerEntity;
import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.repository.game.PlayerRepository;
import com.pw.quizwhizz.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findByIdAndGame(Long id, Game game) {
        PlayerEntity playerEntity = playerRepository.findOne(id);
        Player player = new Player(playerEntity.getName(), game);
        player.setId(id);
        if(playerEntity.getGamesPlayed() != null) {
            player.setGamesPlayed(playerEntity.getGamesPlayed());
        }
        if(playerEntity.getXp() != null) {
            player.setXp(playerEntity.getXp());
        }
        return player;
    }

    @Transactional
    @Override
    public void updateEntity(Player player) {
        PlayerEntity playerEntity = playerRepository.findOne(player.getId());
        playerEntity.setGamesPlayed(player.getGamesPlayed());
        playerEntity. setXp(player.getXp());
        playerRepository.saveAndFlush(playerEntity);
    }

}
