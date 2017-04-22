package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.gameLogic.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
