package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium udostepnia encje AnswerEntity
 * @author Michał Nowiński
 * @see JpaRepository
 */
@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

}
