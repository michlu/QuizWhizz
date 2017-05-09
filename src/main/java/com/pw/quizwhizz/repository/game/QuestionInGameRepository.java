package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.QuestionInGameEntity;
import com.pw.quizwhizz.entity.game.QuestionInGameKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionInGameRepository extends JpaRepository<QuestionInGameEntity, QuestionInGameKey> {

    List<QuestionInGameEntity> findAllById_GameId(Long id);

}
