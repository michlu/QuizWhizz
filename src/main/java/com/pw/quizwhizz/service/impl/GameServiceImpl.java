package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.dto.game.*;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.model.game.*;
import com.pw.quizwhizz.repository.game.*;
import com.pw.quizwhizz.service.*;
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
    private final PlayerInGameRepository playerInGameRepository;
    private final QuestionInGameRepository questionInGameRepository;
    private final QuestionService questionService;
    private final CategoryService categoryService;
    private final AnswerService answerService;
    private final PlayerService playerService;
    private final ScoreService scoreService;
    private final GameFactory gameFactory;
    private final GameDTOBuilder gameDTOBuilder;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository,
                           PlayerInGameRepository playerInGameRepository,
                           QuestionInGameRepository questionInGameRepository,
                           QuestionService questionService,
                           CategoryService categoryService,
                           AnswerService answerService, PlayerService playerService, ScoreService scoreService, GameFactory gameFactory,
                           GameDTOBuilder gameDTOBuilder) {
        this.gameRepository = gameRepository;
        this.questionInGameRepository = questionInGameRepository;
        this.questionService = questionService;
        this.categoryService = categoryService;
        this.answerService = answerService;
        this.playerService = playerService;
        this.scoreService = scoreService;
        this.gameFactory = gameFactory;
        this.gameDTOBuilder = gameDTOBuilder;
        this.playerInGameRepository = playerInGameRepository;
    }

    @Transactional
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

    @Transactional
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
        List<QuestionInGameDTO> questionsInGame = questionInGameRepository.findAllById_GameId(gameId);
        List<Question> questions = convertToQuestions(questionsInGame);
        Category category = categoryService.findById(gameDTO.getCategory().getId());
        Game game = gameFactory.build(category, questions);
        game.getGameStateMachine().setCurrentState(gameDTO.getCurrentState());
        game.setId(gameDTO.getId());
        if(gameDTO.getStartTime() != null) {
            game.getGameStateMachine().setStartTime(gameDTO.getStartTime());
        }
        return game;
    }

    private List<Question> convertToQuestions(List<QuestionInGameDTO> questionsInGame) {
        List<Question> questions = new ArrayList<>();
        for (QuestionInGameDTO questionInGame : questionsInGame) {
            Question question =  questionService.findById(questionInGame.getId().getQuestionId());
            questions.add(question);
        }
        return questions;
    }

    @Override
    @Transactional
    public void startGame(Game game, User user) throws IllegalNumberOfQuestionsException {
        PlayerInGameDTO playerInGameDTO = getPlayerInGameDTO(game, user);
        Player player = new Player(user.getFirstName(), game);
        player.setOwner(playerInGameDTO.isOwner());

        if(player.isOwner()) {
            player.startGame();
            System.out.println("Game state: " + game.getGameStateMachine().getCurrentState());
            System.out.println("Start time: " + game.getGameStateMachine().getStartTime());
        }
        GameDTO gameDTO = convertToGameDTO(game);
        gameDTO.setId(game.getId());
        gameRepository.saveAndFlush(gameDTO);
    }

    @Override
    public void submitAnswers(Game game, User user, List<Long> answerIds) throws IllegalTimeOfAnswerSubmissionException {
        PlayerInGameDTO playerInGameDTO = getPlayerInGameDTO(game, user);
        Player player = playerService.findByIdAndGame(user.getId(), game);
        player.setOwner(playerInGameDTO.isOwner());
        List<Answer> answers = answerService.findAnswersByIds(answerIds);
        player.submitAnswers(answers);
        updateGameDTO(game);
        playerService.updateAsDTO(player);

        //TODO: add score
        //TODO: update the game when the state changes
        //TODO: check scores and determine which one is the highest when GameState == CLOSED
    }

    @Override
    public List<Game> findAll() {
        List<GameDTO> gamesDTO = gameRepository.findAll();
        for(GameDTO gameDTO : gamesDTO) {
            Category category = categoryService.findById(gameDTO.getCategory().getId());
            //TODO: get questions, state etc. and build with the factory
        }
        return null;
    }

    private PlayerInGameDTO getPlayerInGameDTO(Game game, User user) {
        PlayerInGameKey compositeKey = new PlayerInGameKey();
        compositeKey.setGameId(game.getId());
        compositeKey.setUserId(user.getId());
        return playerInGameRepository.findOne(compositeKey);
    }

    private GameDTO convertToGameDTO(Game game) {
        GameState currentState = game.getGameStateMachine().getCurrentState();
        CategoryDTO categoryDTO = categoryService.findCategoryDTOById(game.getCategory().getId());
        GameDTO gameDTO = gameDTOBuilder.withCategory(categoryDTO)
                .withCurrentState(currentState)
                .build();
        Instant startTime = game.getGameStateMachine().getStartTime();
        if(startTime != null) {
            gameDTO.setStartTime(startTime);
        }
        return gameDTO;
    }

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

    private void updateGameDTO(Game game) {
        GameDTO gameDTO = gameRepository.findOne(game.getId());
        gameDTO.setCurrentState(game.getGameStateMachine().getCurrentState());
        gameRepository.saveAndFlush(gameDTO);
    }
}
