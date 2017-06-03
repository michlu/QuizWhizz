package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.GameEntity;
import com.pw.quizwhizz.model.game.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repozytorium udostepnia encje GameEntity
 * @author Karolina Prusaczyk
 * @see JpaRepository
 */
@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    List<GameEntity> findAllByCurrentState(GameState gameState);
}
