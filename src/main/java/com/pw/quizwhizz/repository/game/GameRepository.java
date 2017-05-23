package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.GameEntity;
import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    List<GameEntity> findAllByCurrentState(GameState gameState);
}
