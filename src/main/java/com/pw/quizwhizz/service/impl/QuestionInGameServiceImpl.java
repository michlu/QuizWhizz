package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.question.QuestionInGameDTO;
import com.pw.quizwhizz.repository.QuestionInGameRepository;
import com.pw.quizwhizz.service.QuestionInGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionInGameServiceImpl implements QuestionInGameService {

    private QuestionInGameRepository questionInGameRepository;

    @Autowired
    public QuestionInGameServiceImpl(QuestionInGameRepository questionInGameRepository) {
        this.questionInGameRepository = questionInGameRepository;
    }


    @Override
    public List<QuestionInGameDTO> findAll() {
        return questionInGameRepository.findAll();
    }

    @Override
    public QuestionInGameDTO findQuestionInGameByGameId(Long gameId) {
        return questionInGameRepository.findByGameId(gameId);
    }

    @Override
    public List<QuestionInGameDTO> convertToQuestionsInGame(List<Question> questions, Long gameId) {
        List<QuestionInGameDTO> questionsInGame = new ArrayList<QuestionInGameDTO>();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            QuestionInGameDTO question = new QuestionInGameDTO(q, gameId, i);
            questionsInGame.add(question);
        }
        return questionsInGame;
    }

    @Override
    public void saveQuestionsInGame(List<QuestionInGameDTO> questions) {
        for (QuestionInGameDTO question : questions) {
            questionInGameRepository.save(question);
        }
    }
}