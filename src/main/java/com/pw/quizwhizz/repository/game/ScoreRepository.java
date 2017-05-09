package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.QuestionEntity;
import com.pw.quizwhizz.entity.game.ScoreKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by karol on 03.05.2017.
 */
@Repository
public interface ScoreRepository extends JpaRepository<QuestionEntity, ScoreKey> {
}
