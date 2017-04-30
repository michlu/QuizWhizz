package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.game.GameStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStatsRepository extends JpaRepository<GameStats, Long> {
    GameStats findByGameId(Long gameId);
}
