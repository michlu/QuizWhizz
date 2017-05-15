package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.entity.game.ScoreEntity;
import com.pw.quizwhizz.entity.game.ScoreKey;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.model.game.Score;
import com.pw.quizwhizz.model.game.ScoreBuilder;
import com.pw.quizwhizz.repository.game.ScoreRepository;
import com.pw.quizwhizz.service.PlayerService;
import com.pw.quizwhizz.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//TODO: Test
@Service
public class ScoreServiceImpl implements ScoreService {
    private final ScoreRepository scoreRepository;
    private final PlayerService playerService;
    private final ScoreBuilder scoreBuilder;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository, PlayerService playerService, ScoreBuilder scoreBuilder) {
        this.scoreRepository = scoreRepository;
        this.playerService = playerService;
        this.scoreBuilder = scoreBuilder;
    }

    @Override
    public Score findByUserAndGame(long userId, Game game) throws IllegalNumberOfQuestionsException {
        ScoreKey key = new ScoreKey();
        key.setGameId(game.getId());
        key.setUserId(userId);
        ScoreEntity scoreEntity = scoreRepository.findOne(key);
        Player player = playerService.findByIdAndGame(userId, game);

        Score score = buildScore(scoreEntity, player);
        return score;
    }

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
    public List<Score> getScoresByGame(Game game) throws IllegalNumberOfQuestionsException {
        List<ScoreEntity> scoreEntityList = scoreRepository.findAllById_GameId(game.getId());
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
