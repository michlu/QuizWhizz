package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.entity.PlayerInGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerInGameRepository extends JpaRepository<PlayerInGameEntity, Long> {
    PlayerInGameEntity findByGameId(Long gameId);
}
