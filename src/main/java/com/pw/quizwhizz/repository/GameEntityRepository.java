package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameEntityRepository extends JpaRepository<GameEntity, Long> {
    GameEntity findByGameId(Long gameId);
}
