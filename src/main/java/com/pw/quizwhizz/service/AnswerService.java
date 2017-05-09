package com.pw.quizwhizz.service;

import com.pw.quizwhizz.entity.game.AnswerEntity;
import com.pw.quizwhizz.model.game.Answer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnswerService {
    Answer findById(long id);
    List<Answer> getAllByQuestionId(long questionId);
    List<AnswerEntity> saveAsEntity(List<Answer> answers);
    List<Answer> findAnswersByIds(List<Long> answerIds);
    @Transactional
    void updateAsEntity(List<Answer> answers);
}
