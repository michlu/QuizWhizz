package com.pw.quizwhizz.service.impl;


import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.GameFactory;
import com.pw.quizwhizz.model.entity.*;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.repository.*;
import com.pw.quizwhizz.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    private final GameDTORepository gameDTORepository;
    private final PlayerInGameRepository playerInGameRepository;
    private final GameStatsRepository gameStatsRepository;
    private final GameFactory gameFactory;

    @Autowired
    public GameServiceImpl(GameDTORepository gameDTORepository,
                           PlayerInGameRepository playerInGameRepository,
                           GameStatsRepository gameStatsRepository,
                           GameFactory gameFactory) {
        this.gameDTORepository = gameDTORepository;
        this.playerInGameRepository = playerInGameRepository;
        this.gameStatsRepository = gameStatsRepository;
        this.gameFactory = gameFactory;
    }

    @Override
    public List<GameDTO> findAll() {
        return gameDTORepository.findAll();
    }

    @Override
    public GameDTO findGameById(Long gameId) {
        return gameDTORepository.findById(gameId);
    }

    @Override
    public void deleteGameById(Long id) {
        gameDTORepository.delete(id);
    }

    @Override
    public Game createGameWithId(Category category, List<Question> questions) throws IllegalNumberOfQuestionsException {
        Game game = gameFactory.build(category, questions);
        GameDTO gameDTO = GameDTO.builder()
                .category(game.getCategory())
                .currentState(game.getGameStateMachine().getCurrentState())
                .build();

        gameDTORepository.save(gameDTO);
        game.setId(gameDTO.getId());
        return game;
    }

    protected GameDTO saveAsGameDTO(Game game) {
        GameDTO gameDTO = GameDTO.builder()
                .category(game.getCategory())
                .currentState(game.getGameStateMachine().getCurrentState())
                .build();

        gameDTORepository.save(gameDTO);
        return gameDTO;
    }

    @Override
    public PlayerInGameDTO findPlayerInGameByGameId(Long gameId) {
        return playerInGameRepository.findByGameId(gameId);
    }

    @Override
    public GameStats findGameStatsByGameId(Long gameId) {
        return gameStatsRepository.findByGameId(gameId);
    }

//
//    @Override
//    public HashMap<Long, Game> findAllGames() {
//        return gameRepository.findAll();
//    }
//
//    @Override
//    public void addGame(Game game) {
//        gameRepository.create(game);
//    }
//
//    @Override
//    public Game findGameById(Long gameId) {
//        return gameRepository.read(gameId);
//    }

//    @Override
//    public void updateGame(Long gameId, Game game) {
//        gameRepository.update(gameId,game);
//    }

//    public PlayerInGame addPlayerInGame(User user, Game game){
//        /*
//        POLACZYC PlayerInGame i PlayerInGameDTO
//        zapisac informacje do repozytorium
//         */
//        return new PlayerInGame(user, gameRepository.read(game.getId()));
//    }
}
