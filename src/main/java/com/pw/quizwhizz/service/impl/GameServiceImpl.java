package com.pw.quizwhizz.service.impl;


import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.GameState;
import com.pw.quizwhizz.model.entity.*;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.repository.*;
import com.pw.quizwhizz.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    GameDTORepository gameDTORepository;
    UserRepository userRepository;
    PlayerInGameRepository playerInGameRepository;
    QuestionInGameRepository questionInGameRepository;
    GameStatsRepository gameStatsRepository;

    @Autowired
    public void GameServiceImpl(GameDTORepository gameEntityRepository,
                                UserRepository userRepository,
                                PlayerInGameRepository playerInGameRepository,
                                QuestionInGameRepository questionInGameRepository,
                                GameStatsRepository gameStatsRepository) {
        this.gameDTORepository = gameEntityRepository;
        this.userRepository = userRepository;
        this.playerInGameRepository = playerInGameRepository;
        this.questionInGameRepository = questionInGameRepository;
        this.gameStatsRepository = gameStatsRepository;
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
    public Game createGame(Category category, List<Question> questions) throws IllegalNumberOfQuestionsException {
        Game game = new Game(category, questions);

        GameDTO gameDTO = new GameDTO();
        gameDTO.setCategory(game.getCategory());
        gameDTO.setCurrentState(GameState.OPEN);
        gameDTORepository.save(gameDTO);

        game.setId(gameDTO.getId());

        // TODO: ma zwracac Game czy void? Co z ID pytan i playera w grze?

        return game;
    }


    @Override
    public PlayerInGameDTO findPlayerInGameByGameId(Long gameId) {
        return playerInGameRepository.findByGameId(gameId);
    }

    @Override
    public QuestionInGameDTO findQuestionInGameByGameId(Long gameId) {
        return questionInGameRepository.findByGameId(gameId);
    }

    @Override
    public GameStats findGameStatsByGameId(Long gameId) {
        return gameStatsRepository.findByGameId(gameId);
    }

    @Override
    public List<QuestionInGameDTO> convertToQuestionsInGame(List<Question> questions, Long gameId) {
        List<QuestionInGameDTO> questionsInGame = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            QuestionInGameDTO question = new QuestionInGameDTO(q, gameId, i);
            questionsInGame.add(question);
        }
            return questionsInGame;
    }

    @Override
    public void saveQuestionsInGame(List<QuestionInGameDTO> questions) {
        for (QuestionInGameDTO question : questions) {
            questionInGameRepository.save(question);
        }
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
