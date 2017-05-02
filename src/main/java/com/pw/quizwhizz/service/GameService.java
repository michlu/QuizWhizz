package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.question.Question;

import java.util.List;

/**
 * Created by karol on 02.05.2017.
 */
public interface GameService {
    Game createGame(List<Question> questions) throws IllegalNumberOfQuestionsException;
}
