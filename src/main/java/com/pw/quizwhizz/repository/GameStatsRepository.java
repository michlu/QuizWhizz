package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.dto.GameStatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStatsRepository extends JpaRepository<GameStatsDTO, Long> {
    GameStatsDTO findByGameId(Long gameId);
}
