package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.entity.game.*;
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


@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final PlayerInGameRepository playerInGameRepository;
    private final QuestionInGameRepository questionInGameRepository;
    private final QuestionService questionService;
    private final CategoryService categoryService;
    private final AnswerService answerService;
    private final PlayerService playerService;
    private final GameFactory gameFactory;
    private final GameEntityBuilder gameEntityBuilder;
    private final ScoreRepository scoreRepository;
    private final ScoreBuilder scoreBuilder;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository,
                           PlayerInGameRepository playerInGameRepository,
                           QuestionInGameRepository questionInGameRepository,
                           QuestionService questionService,
                           CategoryService categoryService,
                           AnswerService answerService, PlayerService playerService, GameFactory gameFactory,
                           GameEntityBuilder gameEntityBuilder, ScoreRepository scoreRepository, ScoreBuilder scoreBuilder) {
        this.gameRepository = gameRepository;
        this.questionInGameRepository = questionInGameRepository;
        this.questionService = questionService;
        this.categoryService = categoryService;
        this.answerService = answerService;
        this.playerService = playerService;
        this.gameFactory = gameFactory;
        this.gameEntityBuilder = gameEntityBuilder;
        this.playerInGameRepository = playerInGameRepository;
        this.scoreRepository = scoreRepository;
        this.scoreBuilder = scoreBuilder;
    }

    @Transactional
    @Override
    public Game createGame(List<Question> questions) throws IllegalNumberOfQuestionsException {
        Category category = questions.get(0).getCategory();
        Game game = gameFactory.build(category, questions);
        GameEntity gameEntity = convertToGameEntity(game);
        gameRepository.save(gameEntity);
        game.setId(gameEntity.getId());
        saveQuestionsInGame(questions, game.getId());
        return game;
    }

    @Transactional
    @Override
    public void addOwnerToGame(Game game, User user) {
        PlayerInGameEntity playerInGameEntity = new PlayerInGameEntity();
        PlayerInGameKey compositeKey = new PlayerInGameKey();
        compositeKey.setGameId(game.getId());
        compositeKey.setUserId(user.getId());
        playerInGameEntity.setId(compositeKey);
        playerInGameEntity.setOwner(true);
        playerInGameRepository.save(playerInGameEntity);
    }

    @Override
    public Game findGameById(Long gameId) throws IllegalNumberOfQuestionsException {
        GameEntity gameEntity = gameRepository.findOne(gameId);
        List<QuestionInGameEntity> questionsInGame = questionInGameRepository.findAllById_GameId(gameId);
        List<Question> questions = convertToQuestions(questionsInGame);
        Category category = categoryService.findById(gameEntity.getCategory().getId());
        Game game = gameFactory.build(category, questions);
        game.getGameStateMachine().setCurrentState(gameEntity.getCurrentState());
        game.setId(gameEntity.getId());
        if (gameEntity.getStartTime() != null) {
            game.getGameStateMachine().setStartTime(gameEntity.getStartTime());
        }
        return game;
    }

    private List<Question> convertToQuestions(List<QuestionInGameEntity> questionsInGame) {
        List<Question> questions = new ArrayList<>();
        for (QuestionInGameEntity questionInGame : questionsInGame) {
            Question question = questionService.findById(questionInGame.getId().getQuestionId());
            questions.add(question);
        }
        return questions;
    }

    @Override
    @Transactional
    public void startGame(Game game, User user) throws IllegalNumberOfQuestionsException {
        PlayerInGameEntity playerInGameEntity = getPlayerInGameEntity(game, user);
        Player player = new Player(user.getFirstName(), game);
        player.setOwner(playerInGameEntity.isOwner());

        if (player.isOwner()) {
            player.startGame();
            System.out.println("Game state: " + game.getGameStateMachine().getCurrentState());
            System.out.println("Start time: " + game.getGameStateMachine().getStartTime());
        }
        GameEntity gameEntity = convertToGameEntity(game);
        gameEntity.setId(game.getId());
        gameRepository.saveAndFlush(gameEntity);
    }

    @Transactional
    @Override
    public void submitAnswers(Game game, User user, List<Long> answerIds) throws IllegalTimeOfAnswerSubmissionException, IllegalNumberOfQuestionsException {
        PlayerInGameEntity playerInGameEntity = getPlayerInGameEntity(game, user);
        Player player = playerService.findByIdAndGame(user.getId(), game);
        player.setOwner(playerInGameEntity.isOwner());
        List<Answer> answers = answerService.findAnswersByIds(answerIds);
        player.submitAnswers(answers);
        updateGameEntity(game);
        playerService.updateEntity(player);

        Score score = game.getScores().stream()
                .filter(s -> s.getPlayer().equals(player))
                .findFirst()
                .orElse(null);
        score.setGameId(game.getId());
        saveAsScoreEntity(score);

        //TODO: update the game when the state changes
        //TODO: check scores and determine which one is the highest when GameState == CLOSED
    }

    @Override
    public Score findScoreByUserAndGame(long userId, long gameId) throws IllegalNumberOfQuestionsException {
        ScoreKey key = new ScoreKey();
        key.setGameId(gameId);
        key.setUserId(userId);
        ScoreEntity scoreEntity = scoreRepository.findOne(key);
        Game game = findGameById(gameId);
        Player player = playerService.findByIdAndGame(userId, game);

        Score score = buildScore(scoreEntity, player);
        return score;
    }

    @Transactional
    @Override
    public void saveAsScoreEntity(Score score) {
        ScoreKey key = new ScoreKey();
        key.setUserId(score.getPlayer().getId());
        key.setGameId(score.getGameId());
        ScoreEntity scoreEntity = new ScoreEntity();
        scoreEntity.setId(key);
        scoreEntity.setPoints(score.getPoints());
        scoreEntity.setIsHighest(score.isHighest());
        scoreRepository.save(scoreEntity);
    }

    //TODO: Test
    @Override
    public List<Score> getScoresByGameId(long gameId) throws IllegalNumberOfQuestionsException {
        Game game = findGameById(gameId);
        List<ScoreEntity> scoreEntityList = scoreRepository.findAllById_GameId(gameId);
        List<Score> scores = new ArrayList<>();

        for (ScoreEntity scoreEntity : scoreEntityList) {
            Player player = playerService.findByIdAndGame(scoreEntity.getId().getUserId(), game);
            Score score = buildScore(scoreEntity, player);
            scores.add(score);
        }
        return scores;
    }

    @Override
    public List<Score> getScoresByPlayer(Player player) {
        List<ScoreEntity> scoreEntityList = scoreRepository.findAllById_UserId(player.getId());
        List<Score> scores = new ArrayList<>();

        for (ScoreEntity scoreEntity : scoreEntityList) {
            Score score = buildScore(scoreEntity, player);
            scores.add(score);
        }
        return scores;
    }


    @Override
    public List<Game> findAll() {
        List<GameEntity> gamesEntity = gameRepository.findAll();
        for (GameEntity gameEntity : gamesEntity) {
            Category category = categoryService.findById(gameEntity.getCategory().getId());
            //TODO: get questions, state etc. and build with the factory
        }
        return null;
    }

    private PlayerInGameEntity getPlayerInGameEntity(Game game, User user) {
        PlayerInGameKey compositeKey = new PlayerInGameKey();
        compositeKey.setGameId(game.getId());
        compositeKey.setUserId(user.getId());
        return playerInGameRepository.findOne(compositeKey);
    }

    private GameEntity convertToGameEntity(Game game) {
        GameState currentState = game.getGameStateMachine().getCurrentState();
        CategoryEntity categoryEntity = categoryService.findCategoryEntityById(game.getCategory().getId());
        GameEntity gameEntity = gameEntityBuilder.withCategory(categoryEntity)
                .withCurrentState(currentState)
                .build();
        Instant startTime = game.getGameStateMachine().getStartTime();
        if (startTime != null) {
            gameEntity.setStartTime(startTime);
        }
        return gameEntity;
    }

    private void saveQuestionsInGame(List<Question> questions, Long gameId) {
        for (QuestionInGameEntity question : convertToQuestionsInGame(questions, gameId)) {
            questionInGameRepository.save(question);
        }
    }

    private List<QuestionInGameEntity> convertToQuestionsInGame(List<Question> questions, Long gameId) {
        List<QuestionInGameEntity> questionsInGame = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            QuestionInGameEntity questionInGame = new QuestionInGameEntity();
            QuestionInGameKey compositeKey = new QuestionInGameKey();
            compositeKey.setGameId(gameId);
            compositeKey.setQuestionId(question.getId());
            questionInGame.setId(compositeKey);
            questionInGame.setSequence(i);
            questionsInGame.add(questionInGame);
        }
        return questionsInGame;
    }

    private void updateGameEntity(Game game) {
        GameEntity gameEntity = gameRepository.findOne(game.getId());
        gameEntity.setCurrentState(game.getGameStateMachine().getCurrentState());
        gameRepository.saveAndFlush(gameEntity);
    }

    private Score buildScore(ScoreEntity scoreEntity, Player player) {
        Score score = scoreBuilder
                .withPlayer(player)
                .withGameId(scoreEntity.getId().getGameId())
                .build();
        if (scoreEntity.getPoints() != null) {
            score.setPoints(scoreEntity.getPoints());
        }
        if (scoreEntity.getIsHighest() != null) {
            score.setHighest(scoreEntity.getIsHighest());
        }
        return score;
    }
}
