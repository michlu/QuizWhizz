package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.QuestionEntity;
import com.pw.quizwhizz.entity.game.ScoreEntity;
import com.pw.quizwhizz.entity.game.ScoreKey;
import com.pw.quizwhizz.model.game.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by karol on 03.05.2017.
 */
@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity, ScoreKey> {
    List<ScoreEntity> findAllById_GameId(Long game_id);
    List<ScoreEntity> findAllById_UserId(Long user_id);
}
