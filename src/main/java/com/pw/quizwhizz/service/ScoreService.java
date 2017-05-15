package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Score;

public interface ScoreService {
    Score findByUserAndGame(long userId, Game game) throws IllegalNumberOfQuestionsException;

    void saveAsScoreEntity(Score score);
}
