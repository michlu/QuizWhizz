package com.pw.quizwhizz.service;

import com.pw.quizwhizz.dto.game.AnswerDTO;
import com.pw.quizwhizz.model.game.Answer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnswerService {
    Answer findById(long id);
    List<Answer> getAllByQuestionId(long questionId);
    List<AnswerDTO> saveAsDTO(List<Answer> answers);
    List<Answer> findAnswersByIds(List<Long> answerIds);
    @Transactional
    void updateAsDTO(List<Answer> answers);
}
