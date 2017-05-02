package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.PlayerInGame;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.category.Category;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.*;
import com.pw.quizwhizz.model.player.Player;
import com.pw.quizwhizz.model.player.PlayerInGameDTO;
import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.question.QuestionInGameDTO;
import com.pw.quizwhizz.repository.*;
import com.pw.quizwhizz.service.GameService;
import com.pw.quizwhizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<GameDTO> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public GameDTO findById(Long gameId) {
        return gameRepository.findById(gameId);
    }

    @Override
    public Game findGameById(Long gameId) throws IllegalNumberOfQuestionsException {
        GameDTO gameDTO = findById(gameId);
        Category category = gameDTO.getCategory();
        List<QuestionInGameDTO> questionsInGame = findQuestionsInGameByGameId(gameId);
        List<Question> questions = convertToQuestions(questionsInGame);
        Game game = gameFactory.build(category, questions);
        game.getGameStateMachine().setCurrentState(gameDTO.getCurrentState());
        if (gameDTO.getStartTime() != null) {
            game.getGameStateMachine().setStartTime(gameDTO.getStartTime());
        }
        return game;
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
    public void saveGame(Game game) {
        GameDTO gameDTO = convertToGameDTO(game);
        gameRepository.save(gameDTO);
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

    @Override
    public void deleteGameById(Long gameId) {
        gameRepository.delete(gameId);
    }

    @Override
    public void startGame(PlayerInGame playerInGame) {
        playerInGame.startGame();
    }

    //TODO: Sort out searching methods

    @Override
    public PlayerInGame findPlayerInGameByUserAndGame(User user, Game game) {
        PlayerInGameDTO dto = playerInGameRepository.findByGameIdAndUserId(game.getId(), user.getId());
        PlayerInGame playerInGame = new PlayerInGame(user.getFirstName(), game);
        return playerInGame;
    }

    @Override
    public List<PlayerInGameDTO> findAllPlayersInGameByGameId(Long gameId) {
        return playerInGameRepository.findAllByGameId(gameId);
    }

    // TODO: upewnic sie, ze to jest ok
    @Override
    public PlayerInGame getNewPlayerInGame(User user, Game game) {
        Player currentPlayer = userService.convertToPlayer(user);
        PlayerInGame playerInGame =  convertToPlayerInGame(currentPlayer, game);
        savePlayerInGame(playerInGame);
        return playerInGame;
    }

    @Transactional
    @Override
    public void savePlayerInGame(PlayerInGame playerInGame) {
        User user = userRepository.findById(playerInGame.getId());
        PlayerInGameDTO playerInGameDTO = new PlayerInGameDTO();
        playerInGameDTO.setUser(user);
        playerInGameDTO.setPlayer(playerInGame);
        playerInGameDTO.setOwner(playerInGame.isOwner());
        playerInGameDTO.setGameId(playerInGame.getGame().getId());
        playerInGameRepository.save(playerInGameDTO);
        System.out.println("Saving PlayerInGameDTO" + playerInGameDTO.getUser());
    }

    // TODO: Upewnic sie, ze ok
    @Override
    public void deletePlayerInGame(PlayerInGame playerInGame) {
        playerInGameRepository.deleteByUserId(playerInGame.getId());
    }
    @Override
    public List<QuestionInGameDTO> findAllQuestionsInGame() {
        return questionInGameRepository.findAll();
    }

    @Override
    public List<QuestionInGameDTO> findQuestionsInGameByGameId(Long gameId) {
        return questionInGameRepository.findAllByGameId(gameId);
    }

    @Override
    public void saveQuestionsInGame(List<Question> questions, Long gameId) {
        for (QuestionInGameDTO question : convertToQuestionsInGame(questions, gameId)) {
            questionInGameRepository.save(question);
        }
    }

    @Override
    public List<Question> convertToQuestions(List<QuestionInGameDTO> questionsInGame) {
        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < questionsInGame.size(); i++) {
            Question question = questionsInGame.get(i).getQuestion();
            questions.add(question);
        }
        return questions;
    }

    @Override
    public void deleteQuestionsInGameByGameId(Long gameId) {
        questionInGameRepository.deleteAllByGameId(gameId);
    }

    private List<QuestionInGameDTO> convertToQuestionsInGame(List<Question> questions, Long gameId) {
        List<QuestionInGameDTO> questionsInGame = new ArrayList<QuestionInGameDTO>();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            QuestionInGameDTO question = new QuestionInGameDTO(q, gameId, i);
            questionsInGame.add(question);
        }
        return questionsInGame;
    }
    private PlayerInGame convertToPlayerInGame(Player player, Game game) {
        PlayerInGame playerInGame = new PlayerInGame(player.getName(), game);
        return playerInGame;
    }

    @Override
    public GameStats findGameStatsByGameId(Long gameId) {
        return gameStatsRepository.findByGameId(gameId);
    }
}
