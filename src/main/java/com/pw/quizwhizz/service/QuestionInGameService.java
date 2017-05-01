package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.question.QuestionInGameDTO;

import java.util.List;

/**
 * Created by Karolina on 30.04.2017.
 */
public interface QuestionInGameService {

    List<QuestionInGameDTO> findAll();
    QuestionInGameDTO findQuestionInGameByGameId(Long gameId);
    List<QuestionInGameDTO> convertToQuestionsInGame(List<Question> questions, Long gameId);
    void saveQuestionsInGame(List<QuestionInGameDTO> questions);
}
