package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.dto.game.PlayerDTO;
import com.pw.quizwhizz.dto.game.PlayerInGameDTO;
import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.repository.game.PlayerInGameRepository;
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
        PlayerDTO playerDTO = playerRepository.findOne(id);
        Player player = new Player(playerDTO.getName(), game);
        player.setId(id);
        if(playerDTO.getGamesPlayed() != null) {
            player.setGamesPlayed(playerDTO.getGamesPlayed());
        }
        if(playerDTO.getXp() != null) {
            player.setXp(playerDTO.getXp());
        }
        return player;
    }

    @Transactional
    @Override
    public void updateAsDTO(Player player) {
        PlayerDTO playerDTO = playerRepository.findOne(player.getId());
        playerDTO.setGamesPlayed(player.getGamesPlayed());
        playerDTO. setXp(player.getXp());
        playerRepository.saveAndFlush(playerDTO);
    }

}
