package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.PlayerInGameEntity;
import com.pw.quizwhizz.entity.game.PlayerInGameKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerInGameRepository extends JpaRepository<PlayerInGameEntity, PlayerInGameKey> {
}
