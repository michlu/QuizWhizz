package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.dto.game.PlayerInGameDTO;
import com.pw.quizwhizz.dto.game.PlayerInGameKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerInGameRepository extends JpaRepository<PlayerInGameDTO, PlayerInGameKey> {
}
