package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Question;

import java.util.List;

/**
 * Created by Karolina on 02.05.2017.
 */
public interface GameService {
    Game createGame(List<Question> questions) throws IllegalNumberOfQuestionsException;
    void addOwnerToGame(Game game, User user);
    Game findGameById(Long id) throws IllegalNumberOfQuestionsException;
    void startGame(Game game, User user) throws IllegalNumberOfQuestionsException;
    List<Game> findAll();
    void submitAnswers(Game game, User user, List<Long> answerIds) throws IllegalTimeOfAnswerSubmissionException;
}