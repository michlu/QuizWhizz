package com.pw.quizwhizz.service.impl;


import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.category.Category;
import com.pw.quizwhizz.model.game.*;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.player.PlayerInGameDTO;
import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.question.QuestionInGameDTO;
import com.pw.quizwhizz.repository.*;
import com.pw.quizwhizz.service.GameDTOService;
import com.pw.quizwhizz.service.QuestionInGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameDTOServiceImpl implements GameDTOService {
    private final GameRepository gameRepository;
    private final PlayerInGameRepository playerInGameRepository;
    private final QuestionInGameService questionInGameService;
    private final GameStatsRepository gameStatsRepository;
    private final GameFactory gameFactory;
    private final GameDTOBuilder builder;

    @Autowired
    public GameDTOServiceImpl(GameRepository gameRepository,
                              PlayerInGameRepository playerInGameRepository,
                              QuestionInGameService questionInGameService, GameStatsRepository gameStatsRepository,
                              GameFactory gameFactory, GameDTOBuilder builder) {
        this.gameRepository = gameRepository;
        this.playerInGameRepository = playerInGameRepository;
        this.questionInGameService = questionInGameService;
        this.gameStatsRepository = gameStatsRepository;
        this.gameFactory = gameFactory;
        this.builder = builder;
    }

    @Override
    public List<GameDTO> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public GameDTO findById(Long gameId) {
        return gameRepository.findById(gameId);
    }

    @Override
    public Game findGameById(Long id) throws IllegalNumberOfQuestionsException {
        GameDTO gameDTO = findById(id);
        Category category = gameDTO.getCategory();
        List<QuestionInGameDTO> questionsInGame = questionInGameService.findQuestionsInGameByGameId(id);
        List<Question> questions = questionInGameService.convertToQuestions(questionsInGame);
        Game game = gameFactory.build(category, questions);
        return game;
    }

    @Override
    public void deleteGameById(Long id) {
        gameRepository.delete(id);
    }

    @Override
    public Game createGameWithId(Category category, List<Question> questions) throws IllegalNumberOfQuestionsException {
        Game game = gameFactory.build(category, questions);
        GameDTO gameDTO = convertToGameDTO(game);
        gameRepository.save(gameDTO);
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
//    public Game findById(Long gameId) {
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
