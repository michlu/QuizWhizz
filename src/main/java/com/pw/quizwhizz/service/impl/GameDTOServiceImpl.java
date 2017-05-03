package com.pw.quizwhizz.service.impl;


import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.category.Category;
import com.pw.quizwhizz.model.game.*;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.player.PlayerInGameDTO;
import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.repository.*;
import com.pw.quizwhizz.service.GameDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameDTOServiceImpl implements GameDTOService {
    private final GameDTORepository gameDTORepository;
    private final PlayerInGameRepository playerInGameRepository;
    private final GameStatsRepository gameStatsRepository;
    private final GameFactory gameFactory;
    private final GameDTOBuilder builder;

    @Autowired
    public GameDTOServiceImpl(GameDTORepository gameDTORepository,
                              PlayerInGameRepository playerInGameRepository,
                              GameStatsRepository gameStatsRepository,
                              GameFactory gameFactory, GameDTOBuilder builder) {
        this.gameDTORepository = gameDTORepository;
        this.playerInGameRepository = playerInGameRepository;
        this.gameStatsRepository = gameStatsRepository;
        this.gameFactory = gameFactory;
        this.builder = builder;
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
        GameDTO gameDTO = convertToGameDTO(game);
        gameDTORepository.save(gameDTO);
        game.setId(gameDTO.getId());
        return game;
    }

    @Override
    public GameDTO convertToGameDTO(Game game) {
        Category category = game.getCategory();
        GameState currentState = game.getGameStateMachine().getCurrentState();
        GameDTO gameDTO = builder.withCategory(category)
                .withCurrentState(currentState)
                .build();
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