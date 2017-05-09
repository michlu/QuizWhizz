package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by karol on 03.05.2017.
 */
@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>{
}
