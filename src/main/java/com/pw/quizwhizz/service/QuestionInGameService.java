package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.question.QuestionInGameDTO;

import java.util.List;

/**
 * Created by Karolina on 30.04.2017.
 */
public interface QuestionInGameService {

    QuestionInGameDTO findByGameId(Long gameId);
    List<QuestionInGameDTO> findAll();
    List <QuestionInGameDTO> findQuestionsInGameByGameId(Long gameId);
    List<QuestionInGameDTO> convertToQuestionsInGame(List<Question> questions, Long gameId);
    List<Question> convertToQuestions(List<QuestionInGameDTO> questionsInGame);
    void saveQuestionsInGame(List<QuestionInGameDTO> questions);
}
