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
    public QuestionInGameDTO findByGameId(Long gameId) {
        return questionInGameRepository.findByGameId(gameId);
    }

    @Override
    public List<QuestionInGameDTO> findAll() {
        return questionInGameRepository.findAll();
    }

    @Override
    public List <QuestionInGameDTO> findQuestionsInGameByGameId(Long gameId) {
        return questionInGameRepository.findAllByGameId(gameId);
    }

    @Override
    public List<Question> convertToQuestions(List<QuestionInGameDTO> questionsInGame) {
        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < questionsInGame.size(); i++) {
            Question question = questionsInGame.get(i).getQuestion();
            questions.add(question);
        }
        return questions;
    }

    @Override
    public void saveQuestionsInGame(List<Question> questions, Long gameId) {


        for (QuestionInGameDTO question : convertToQuestionsInGame(questions, gameId)) {
            questionInGameRepository.save(question);
        }
    }

    private List<QuestionInGameDTO> convertToQuestionsInGame(List<Question> questions, Long gameId) {
        List<QuestionInGameDTO> questionsInGame = new ArrayList<QuestionInGameDTO>();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            QuestionInGameDTO question = new QuestionInGameDTO(q, gameId, i);
            questionsInGame.add(question);
        }
        return questionsInGame;
    }
}