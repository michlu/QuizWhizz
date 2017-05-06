package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.dto.game.AnswerDTO;
import com.pw.quizwhizz.model.game.Answer;
import com.pw.quizwhizz.repository.game.AnswerRepository;
import com.pw.quizwhizz.repository.game.QuestionRepository;
import com.pw.quizwhizz.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService{

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public Answer findById(long id) {
        AnswerDTO answerDTO = answerRepository.findOne(id);
        Answer answer = convertToAnswer(answerDTO);
        return answer;
    }

    @Override
    public List<Answer> getAllByQuestionId(long questionId) {
        List<AnswerDTO> answersDTO = questionRepository.findOne(questionId).getAnswers();
        List<Answer> answers = new ArrayList<>();
        for (AnswerDTO answerDTO : answersDTO) {
            Answer answer = convertToAnswer(answerDTO);
            answers.add(answer);
        }
        return answers;
    }

    @Transactional
    @Override
    public List<AnswerDTO> saveAsDTO(List<Answer> answers) {
        List<AnswerDTO> answersDTO = new ArrayList<>();
        for (Answer answer : answers) {
            AnswerDTO answerDTO = new AnswerDTO();
            answerDTO.setAnswer(answer.getAnswer());
            answerDTO.setCorrect(answer.getIsCorrect());
            answerRepository.save(answerDTO);
            answer.setId(answerDTO.getId());
            answersDTO.add(answerDTO);
        }
        return answersDTO;
    }

    private Answer convertToAnswer(AnswerDTO answerDTO) {
        Answer answer = new Answer();
        answer.setId(answerDTO.getId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setCorrect(answerDTO.getIsCorrect());
        return answer;
    }
}
