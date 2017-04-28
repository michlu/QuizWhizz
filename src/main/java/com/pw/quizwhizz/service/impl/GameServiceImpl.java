package com.pw.quizwhizz.service.impl;


import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.PlayerInGame;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.entity.*;
import com.pw.quizwhizz.repository.*;
import com.pw.quizwhizz.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    GameRepository gameRepository;
    GameEntityRepository gameEntityRepository;
    PlayerInGameRepository playerInGameRepository;
    QuestionInGameRepository questionInGameRepository;
    GameStatsRepository gameStatsRepository;

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    @Autowired
    public void setGameEntityRepository(GameEntityRepository gameEntityRepository) {
        this.gameEntityRepository = gameEntityRepository;
    }
    @Autowired
    public void setPlayerInGameRepository(PlayerInGameRepository playerInGameRepository) {
        this.playerInGameRepository = playerInGameRepository;
    }
    @Autowired
    public void setQuestionInGameRepository(QuestionInGameRepository questionInGameRepository) {
        this.questionInGameRepository = questionInGameRepository;
    }
    @Autowired
    public void setGameStatsRepository(GameStatsRepository gameStatsRepository) {
        this.gameStatsRepository = gameStatsRepository;
    }

    @Override
    public HashMap<Long, Game> findAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public void addGame(Game game) {
        gameRepository.create(game);
    }

    @Override
    public Game findGameById(Long gameId) {
        return gameRepository.read(gameId);
    }

    @Override
    public void deleteGameById(Long gameId) {
        gameRepository.delete(gameId);
    }

    @Override
    public void updateGame(Long gameId, Game game) {
        gameRepository.update(gameId,game);
    }

//    public PlayerInGame addPlayerInGame(User user, Game game){
//        /*
//        POLACZYC PlayerInGame i PlayerInGameEntity
//        zapisac informacje do repozytorium
//         */
//        return new PlayerInGame(user, gameRepository.read(game.getId()));
//    }

    @Override
    public GameEntity findGameEntityByGameId(Long gameId) {
        return gameEntityRepository.findByGameId(gameId);
    }

    @Override
    public PlayerInGameEntity findPlayerInGameEntityByGameId(Long gameId) {
        return playerInGameRepository.findByGameId(gameId);
    }

    @Override
    public QuestionInGameEntity findQuestionInGameEntityByGameId(Long gameId) {
        return questionInGameRepository.findByGameId(gameId);
    }

    @Override
    public GameStats findGameStatsByGameId(Long gameId) {
        return gameStatsRepository.findByGameId(gameId);
    }

    @Override
    public List<QuestionInGameEntity> convertToQuestionsInGame(List<Question> questions, Long gameId) {
        List<QuestionInGameEntity> questionsInGame = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            QuestionInGameEntity question = new QuestionInGameEntity(q, gameId, i);
            questionsInGame.add(question);
        }
            return questionsInGame;
    }

    @Override
    public void saveQuestionsInGame(List<QuestionInGameEntity> questions) {
        for (QuestionInGameEntity question : questions) {
            questionInGameRepository.save(question);
        }
    }
}
