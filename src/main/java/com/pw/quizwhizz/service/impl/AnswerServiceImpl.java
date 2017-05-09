package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.entity.game.AnswerEntity;
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
        AnswerEntity answerEntity = answerRepository.findOne(id);
        Answer answer = convertToAnswer(answerEntity);
        return answer;
    }

    @Override
    public List<Answer> getAllByQuestionId(long questionId) {
        List<AnswerEntity> answersEntity = questionRepository.findOne(questionId).getAnswers();
        List<Answer> answers = new ArrayList<>();
        for (AnswerEntity answerEntity : answersEntity) {
            Answer answer = convertToAnswer(answerEntity);
            answers.add(answer);
        }
        return answers;
    }

    @Transactional
    @Override
    public List<AnswerEntity> saveAsEntity(List<Answer> answers) {
        List<AnswerEntity> answersEntity = new ArrayList<>();
        for (Answer answer : answers) {
            AnswerEntity answerEntity = new AnswerEntity();
            answerEntity.setAnswer(answer.getAnswer());
            answerEntity.setCorrect(answer.getIsCorrect());
            answerRepository.save(answerEntity);
            answer.setId(answerEntity.getId());
            answersEntity.add(answerEntity);
        }
        return answersEntity;
    }

    @Override
    public List<Answer> findAnswersByIds(List<Long> answerIds) {
        List<Answer> answers = new ArrayList<>();

        for(Long id : answerIds) {
            Answer answer = findById(id);
            answers.add(answer);
        }
        return answers;
    }

    @Transactional
    @Override
    public void updateAsEntity(List<Answer> answers) {
        List<AnswerEntity> answersEntity = new ArrayList<>();
        for (Answer answer : answers) {
            AnswerEntity answerEntity = new AnswerEntity();
            answerEntity.setAnswer(answer.getAnswer());
            answerEntity.setCorrect(answer.getIsCorrect());
            answerEntity.setId(answer.getId());
            answerRepository.saveAndFlush(answerEntity);
            answersEntity.add(answerEntity);
        }
    }

    private Answer convertToAnswer(AnswerEntity answerEntity) {
        Answer answer = new Answer();
        answer.setId(answerEntity.getId());
        answer.setAnswer(answerEntity.getAnswer());
        answer.setCorrect(answerEntity.getIsCorrect());
        return answer;
    }
}
