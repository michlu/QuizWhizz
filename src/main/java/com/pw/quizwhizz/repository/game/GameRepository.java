package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
}
