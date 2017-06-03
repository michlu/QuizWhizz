package com.pw.quizwhizz.service;

import com.pw.quizwhizz.entity.game.AnswerEntity;
import com.pw.quizwhizz.model.game.Answer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Abstrakcyjna warstwa serwisu
 * @author Michał Nowiński, Karolina Prusaczyk
 */
public interface AnswerService {
    Answer findById(long id);
    List<Answer> getAnswersByQuestionId(long questionId);
    List<AnswerEntity> saveAnswers(List<Answer> answers);
    List<Answer> findAnswersByIds(List<Long> answerIds);
    @Transactional
    void updateAnswers(List<Answer> answers);
}
