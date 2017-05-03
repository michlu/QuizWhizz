package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.dto.game.ScoreKey;
import com.pw.quizwhizz.model.game.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by karol on 03.05.2017.
 */
@Repository
public interface ScoreRepository extends JpaRepository<Question, ScoreKey> {
}
