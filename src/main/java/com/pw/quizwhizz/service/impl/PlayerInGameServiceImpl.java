package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.player.Player;
import com.pw.quizwhizz.model.PlayerInGame;
import com.pw.quizwhizz.model.player.PlayerInGameDTO;
import com.pw.quizwhizz.repository.GameRepository;
import com.pw.quizwhizz.repository.PlayerInGameRepository;
import com.pw.quizwhizz.repository.UserRepository;
import com.pw.quizwhizz.service.PlayerInGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Karolina on 30.04.2017.
 */
@Service
public class PlayerInGameServiceImpl implements PlayerInGameService {
    private final PlayerInGameRepository playerInGameRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Autowired
    public PlayerInGameServiceImpl(PlayerInGameRepository playerInGameRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.playerInGameRepository = playerInGameRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public PlayerInGame convertToPlayerInGame(Player player, Game game) {
        PlayerInGame playerInGame = new PlayerInGame(
                player.getName(),
                game);
        return playerInGame;
    }

    @Override
    public PlayerInGameDTO convertToPlayerInGameDTO(PlayerInGame playerInGame) {
        User user = userRepository.findById(playerInGame.getId());
        PlayerInGameDTO playerInGameDTO = new PlayerInGameDTO();
        playerInGameDTO.setUser(user);
        playerInGameDTO.setPlayer(playerInGame);
        playerInGameDTO.setOwner(playerInGame.isOwner());
        playerInGameDTO.setGameId(playerInGame.getGame().getId());
        return playerInGameDTO;
    }

    @Override
    public void savePlayerInGameDTO(PlayerInGameDTO playerInGameDTO) {
        playerInGameRepository.save(playerInGameDTO);
    }
}
