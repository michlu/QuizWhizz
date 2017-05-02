package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.PlayerInGame;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.player.PlayerInGameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerInGameRepository extends JpaRepository<PlayerInGameDTO, Long> {
    List<PlayerInGameDTO> findAllByGameId(Long gameId);
    PlayerInGameDTO findByGameIdAndUserId(Long gameId, Long userId);
    PlayerInGameDTO findByUserId(Long userId);
    void deleteByUserId(Long playerInGameId);
}
