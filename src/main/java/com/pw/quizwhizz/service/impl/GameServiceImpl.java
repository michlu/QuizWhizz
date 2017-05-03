package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.dto.game.*;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.model.game.*;
import com.pw.quizwhizz.repository.*;
import com.pw.quizwhizz.repository.game.GameRepository;
import com.pw.quizwhizz.repository.game.PlayerInGameRepository;
import com.pw.quizwhizz.repository.game.QuestionInGameRepository;
import com.pw.quizwhizz.service.GameService;
import com.pw.quizwhizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karolina on 02.05.2017.
 */

// TODO: Update'owanie zasobow

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final QuestionInGameRepository questionInGameRepository;
    private final PlayerInGameRepository playerInGameRepository;
    private final GameStatsRepository gameStatsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    private final GameFactory gameFactory;
    private final GameDTOBuilder gameDTOBuilder;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository,
                           PlayerInGameRepository playerInGameRepository,
                           QuestionInGameRepository questionInGameRepository,
                           GameStatsRepository gameStatsRepository,
                           UserRepository userRepository, UserService userService, GameFactory gameFactory,
                           GameDTOBuilder gameDTOBuilder) {
        this.gameRepository = gameRepository;
        this.questionInGameRepository = questionInGameRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.gameFactory = gameFactory;
        this.gameDTOBuilder = gameDTOBuilder;
        this.playerInGameRepository = playerInGameRepository;
        this.gameStatsRepository = gameStatsRepository;
    }

    @Override
    public Game createGame(List<Question> questions) throws IllegalNumberOfQuestionsException {
        Category category = questions.get(0).getCategory();
        Game game = gameFactory.build(category, questions);
        GameDTO gameDTO = convertToGameDTO(game);
        gameRepository.save(gameDTO);
        game.setId(gameDTO.getId());
        saveQuestionsInGame(questions, game.getId());
        return game;
    }

    @Override
    public void addOwnerToGame(Game game, User user) {
        PlayerInGameDTO playerInGameDTO = new PlayerInGameDTO();
        PlayerInGameKey compositeKey = new PlayerInGameKey();
        compositeKey.setGameId(game.getId());
        compositeKey.setUserId(user.getId());
        playerInGameDTO.setId(compositeKey);
        playerInGameDTO.setOwner(true);
        playerInGameRepository.save(playerInGameDTO);
    }

    @Override
    public Game findGameById(Long gameId) throws IllegalNumberOfQuestionsException {
        GameDTO gameDTO = gameRepository.findOne(gameId);
        // TODO:  gameDTO => game
        return null;
    }

    @Override
    public void startGame(Game game, User user) {
        // TODO:
    }

    @Override
    public List<Game> findAll() {
        List<GameDTO> gamesDTO = gameRepository.findAll();
        // TODO:  gamesDTO => games
        return null;
    }

    private GameDTO convertToGameDTO(Game game) {
        Category category = game.getCategory();
        GameState currentState = game.getGameStateMachine().getCurrentState();
        GameDTO gameDTO = gameDTOBuilder.withCategory(category)
                .withCurrentState(currentState)
                .build();
        Instant startTime = game.getGameStateMachine().getStartTime();
        if(startTime != null) {
            gameDTO.setStartTime(startTime);
        }
        return gameDTO;
    }

//    @Override
//    public Player findPlayerByUserAndGame(User user, Game game) {
//        PlayerInGameKey compositeKey = new PlayerInGameKey();
//        compositeKey.setUserId(user.getId());
//        compositeKey.setGameId(game.getId());
//        PlayerInGameDTO dto = playerInGameRepository.findOne(compositeKey);
//        return new Player(user.getFirstName(), game);
//    }

    private void saveQuestionsInGame(List<Question> questions, Long gameId) {
        for (QuestionInGameDTO question : convertToQuestionsInGame(questions, gameId)) {
            questionInGameRepository.save(question);
        }
    }

    private List<QuestionInGameDTO> convertToQuestionsInGame(List<Question> questions, Long gameId) {
        List<QuestionInGameDTO> questionsInGame = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            QuestionInGameDTO questionInGame = new QuestionInGameDTO();
            QuestionInGameKey compositeKey = new QuestionInGameKey();
            compositeKey.setGameId(gameId);
            compositeKey.setQuestionId(question.getId());
            questionInGame.setId(compositeKey);
            questionInGame.setSequence(i);

            questionsInGame.add(questionInGame);
        }

        return questionsInGame;
    }
}
