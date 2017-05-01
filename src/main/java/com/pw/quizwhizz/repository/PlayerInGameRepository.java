package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.player.PlayerInGameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerInGameRepository extends JpaRepository<PlayerInGameDTO, Long> {
    PlayerInGameDTO findByGameId(Long gameId);
}
