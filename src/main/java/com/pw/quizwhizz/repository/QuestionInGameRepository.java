package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.question.QuestionInGameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionInGameRepository extends JpaRepository<QuestionInGameDTO, Long> {
    QuestionInGameDTO findByGameId(Long gameId);
    List<QuestionInGameDTO> findAllByGameId(Long gameId);
}
