package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium udostepnia encje PlayerEntity
 * @author Karolina Prusaczyk
 * @see JpaRepository
 */
@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>{
}
