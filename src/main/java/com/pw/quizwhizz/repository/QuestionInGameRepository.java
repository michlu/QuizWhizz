package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.entity.QuestionInGameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionInGameRepository extends JpaRepository<QuestionInGameDTO, Long> {
    QuestionInGameDTO findByGameId(Long gameId);
}
