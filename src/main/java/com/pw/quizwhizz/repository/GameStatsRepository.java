package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.entity.GameStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStatsRepository extends JpaRepository<GameStatsEntity, Long> {
    GameStatsEntity findByGameId(Long gameId);
}
