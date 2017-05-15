package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.model.game.Score;

import java.util.List;

public interface ScoreService {
    Score findByUserAndGame(long userId, Game game) throws IllegalNumberOfQuestionsException;

    void saveAsScoreEntity(Score score);

    List<Score> getScoresByGame(Game game) throws IllegalNumberOfQuestionsException;

    List<Score> getScoresByPlayer(Player player);
}
