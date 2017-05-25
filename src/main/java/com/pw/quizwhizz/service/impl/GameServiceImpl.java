package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.entity.game.*;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.exception.ScoreCannotBeRetrievedBeforeGameIsClosedException;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.model.game.*;
import com.pw.quizwhizz.repository.game.*;
import com.pw.quizwhizz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final PlayerInGameRepository playerInGameRepository;
    private final QuestionInGameRepository questionInGameRepository;
    private final QuestionService questionService;
    private final CategoryService categoryService;
    private final AnswerService answerService;
    private final PlayerRepository playerRepository;
    private final GameFactory gameFactory;
    private final GameEntityBuilder gameEntityBuilder;
    private final ScoreRepository scoreRepository;
    private final ScoreBuilder scoreBuilder;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository,
                           PlayerInGameRepository playerInGameRepository,
                           PlayerRepository playerRepository, ScoreRepository scoreRepository, QuestionInGameRepository questionInGameRepository,
                           QuestionService questionService,
                           CategoryService categoryService,
                           AnswerService answerService, GameFactory gameFactory,
                           GameEntityBuilder gameEntityBuilder, ScoreBuilder scoreBuilder) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.questionInGameRepository = questionInGameRepository;
        this.questionService = questionService;
        this.categoryService = categoryService;
        this.answerService = answerService;
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
        PlayerInGameEntity playerInGameEntity = createPlayerInGameEntityWithCompositeKey(game, user);
        playerInGameEntity.setOwner(true);
        playerInGameRepository.save(playerInGameEntity);
    }

    @Override
    public void addPlayerToGame(Game game, User user) {
        PlayerInGameKey key = new PlayerInGameKey();
        key.setUserId(user.getId());
        key.setGameId(game.getId());
        Optional<PlayerInGameEntity> optionalPlayer = Optional.ofNullable(playerInGameRepository.findOne(key));
        if (optionalPlayer.isPresent()) {
            return;

        } else {
            Player player = new Player(user.getFirstName());
            player.setGame(game);
            PlayerInGameEntity playerInGameEntity = createPlayerInGameEntityWithCompositeKey(game, user);
            playerInGameEntity.setOwner(player.isOwner());
            playerInGameRepository.save(playerInGameEntity);
        }
    }

    @Override
    public Game findGameById (Long gameId) throws IllegalNumberOfQuestionsException {
        GameEntity gameEntity = gameRepository.findOne(gameId);
        List<QuestionInGameEntity> questionsInGame = questionInGameRepository.findAllById_GameId(gameId);
        List<Question> questions = convertToQuestions(questionsInGame);
        Category category = categoryService.findById(gameEntity.getCategory().getId());
        Game game = buildGame(gameEntity, category, questions);
        return game;
    }

    @Override
    @Transactional
    public boolean isGameStarted (Long gameId){
        GameEntity game = gameRepository.getOne(gameId);

        return game.getCurrentState() == GameState.STARTED;
    }

    @Override
    @Transactional
    public void startGame (Game game, User user) throws IllegalNumberOfQuestionsException {
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
    public void submitAnswers (Game game, User user, List < Long > answerIds) throws
            IllegalTimeOfAnswerSubmissionException, IllegalNumberOfQuestionsException {
        PlayerInGameEntity playerInGameEntity = getPlayerInGameEntity(game, user);
        Player player = findPlayerByIdAndGame(user.getId(), game);
        player.setOwner(playerInGameEntity.isOwner());
        List<Answer> answers = answerService.findAnswersByIds(answerIds);
        player.submitAnswers(answers);
        updateGame(game);
        updatePlayer(player);

        Score score = game.getScores().stream()
                .filter(s -> s.getPlayer().equals(player))
                .findFirst()
                .orElse(null);
        score.setGameId(game.getId());
        saveScore(score);
    }

    //TODO: test
    // TODO: update the game when the state changes
    // TODO: check scores and determine which one is the highest when GameState == CLOSED

    @Override
    public Score findScoreByUserAndGame ( long userId, long gameId) throws IllegalNumberOfQuestionsException {
        ScoreKey key = new ScoreKey();
        key.setGameId(gameId);
        key.setUserId(userId);
        ScoreEntity scoreEntity = scoreRepository.findOne(key);
        Game game = findGameById(gameId);
        Player player = findPlayerByIdAndGame(userId, game);

        Score score = buildScore(scoreEntity, player);
        return score;
    }

    @Transactional
    @Override
    public void saveScore (Score score) {
        ScoreKey key = getScoreKey(score);
        ScoreEntity scoreEntity = new ScoreEntity();
        scoreEntity.setId(key);
        scoreEntity.setPoints(score.getPoints());
        scoreEntity.setIsHighest(score.isHighest());
        scoreRepository.save(scoreEntity);
    }


    @Override
    public List<Score> getScoresByGameId ( long gameId) throws IllegalNumberOfQuestionsException, ScoreCannotBeRetrievedBeforeGameIsClosedException {
        Game game = findGameById(gameId);
        updateGame(game);
        List<ScoreEntity> scoreEntityList = scoreRepository.findAllById_GameId(gameId);
        List<Score> scores = new ArrayList<>();

        for (ScoreEntity scoreEntity : scoreEntityList) {
            Player player = findPlayerByIdAndGame(scoreEntity.getId().getUserId(), game);
            Score score = buildScore(scoreEntity, player);
            scores.add(score);
        }
        game.setScores(scores);
        game.checkScores();

        for (Score score : scores) {
            updateScore(score);
            updatePlayer(score.getPlayer());
        }
        return scores;
    }

    @Override
    public List<Score> getScoresByPlayer (Player player){
        List<ScoreEntity> scoreEntityList = scoreRepository.findAllById_UserId(player.getId());
        List<Score> scores = new ArrayList<>();

        for (ScoreEntity scoreEntity : scoreEntityList) {
            Score score = buildScore(scoreEntity, player);
            scores.add(score);
        }
        return scores;
    }

    @Transactional
    @Override
    public List<Game> getAllOpenGames () throws IllegalNumberOfQuestionsException {
        List<Game> games = new ArrayList<>();
        List<GameEntity> gameEntities = gameRepository.findAllByCurrentState(GameState.OPEN);
        for (GameEntity gameEntity : gameEntities) {
            Category category = categoryService.findById(gameEntity.getCategory().getId());
            List<QuestionInGameEntity> questionsInGame = questionInGameRepository.findAllById_GameId(gameEntity.getId());
            List<Question> questions = convertToQuestions(questionsInGame);
            List<PlayerInGameEntity> playersInGame = playerInGameRepository.findAllById_GameId(gameEntity.getId());
            Game game = buildGame(gameEntity, category, questions);
            List<Player> players = convertToPlayers(playersInGame, game);
            game.setPlayers(players);
            games.add(game);
        }
        return games;
    }

    @Override
    public List<String> getNamesOfPlayersInGame (Long gameId){
        List<PlayerInGameEntity> playerInGameEntities = playerInGameRepository.findAllById_GameId(gameId);
        List<Long> playerIds = new ArrayList<>();

        for (PlayerInGameEntity playerInGameEntity : playerInGameEntities) {
            long playerId = playerInGameEntity.getId().getUserId();
            playerIds.add(playerId);
        }
        List<PlayerEntity> playerEntities = playerRepository.findAll(playerIds);

        List<String> playerNames = new ArrayList<>();
        for (PlayerEntity playerEntity : playerEntities) {
            playerNames.add(playerEntity.getName());
        }

        return playerNames;
    }

    @Override
    public boolean isPlayerGameOwner (Long userId, Long gameId){
        PlayerInGameKey key = new PlayerInGameKey();
        key.setGameId(gameId);
        key.setUserId(userId);
        PlayerInGameEntity playerInGameEntity = playerInGameRepository.findOne(key);
        boolean isOwner = playerInGameEntity.isOwner();
        return isOwner;
    }

    private Player findPlayerByIdAndGame(Long id, Game game) throws IllegalNumberOfQuestionsException {
        PlayerEntity playerEntity = playerRepository.findOne(id);
        Player player = new Player(playerEntity.getName(), game);
        player.setId(id);
        if (playerEntity.getGamesPlayed() != null) {
            player.setGamesPlayed(playerEntity.getGamesPlayed());
        }
        if (playerEntity.getXp() != null) {
            player.setXp(playerEntity.getXp());
        }
        return player;
    }

    @Transactional
    private void updatePlayer(Player player) {
        PlayerEntity playerEntity = playerRepository.findOne(player.getId());
        playerEntity.setGamesPlayed(player.getGamesPlayed());
        playerEntity.setXp(player.getXp());
        playerRepository.saveAndFlush(playerEntity);
    }

    private Game buildGame(GameEntity gameEntity, Category category, List<Question> questions) throws IllegalNumberOfQuestionsException {
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

    private PlayerInGameEntity getPlayerInGameEntity(Game game, User user) {
        PlayerInGameKey compositeKey = new PlayerInGameKey();
        compositeKey.setGameId(game.getId());
        compositeKey.setUserId(user.getId());
        return playerInGameRepository.findOne(compositeKey);
    }

    private List<Player> convertToPlayers(List<PlayerInGameEntity> playersInGame, Game game) throws IllegalNumberOfQuestionsException {
        List<Player> players = new ArrayList<>();
        for (PlayerInGameEntity playerInGameEntity : playersInGame) {
            Player player = findPlayerByIdAndGame(playerInGameEntity.getId().getUserId(), game);
            players.add(player);
        }
        return players;
    }

    private PlayerInGameEntity createPlayerInGameEntityWithCompositeKey(Game game, User user) {
        PlayerInGameEntity playerInGameEntity = new PlayerInGameEntity();
        PlayerInGameKey compositeKey = new PlayerInGameKey();
        compositeKey.setGameId(game.getId());
        compositeKey.setUserId(user.getId());
        playerInGameEntity.setId(compositeKey);
        return playerInGameEntity;
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

    //TODO: FIX updating state
    private void updateGame(Game game) {
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
    private ScoreKey getScoreKey(Score score) {
        ScoreKey key = new ScoreKey();
        key.setUserId(score.getPlayer().getId());
        key.setGameId(score.getGameId());
        return key;
    }

    @Transactional
    private void updateScore(Score score) {
        ScoreKey key = new ScoreKey();
        key.setGameId(score.getGameId());
        key.setUserId(score.getPlayer().getId());
        ScoreEntity scoreEntity = scoreRepository.findOne(key);
        scoreEntity.setPoints(score.getPoints());
        scoreEntity.setIsHighest(score.isHighest());
        scoreRepository.saveAndFlush(scoreEntity);
    }
}
