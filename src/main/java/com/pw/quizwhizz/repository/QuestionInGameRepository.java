package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.entity.QuestionInGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionInGameRepository extends JpaRepository<QuestionInGameEntity, Long> {
    QuestionInGameEntity findByGameId(Long gameId);
}
