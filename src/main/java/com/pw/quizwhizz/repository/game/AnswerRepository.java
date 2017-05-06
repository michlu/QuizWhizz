package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.dto.game.AnswerDTO;
import com.pw.quizwhizz.model.game.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerDTO, Long> {

}
