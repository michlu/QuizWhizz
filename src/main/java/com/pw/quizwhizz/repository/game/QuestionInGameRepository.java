package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.dto.game.QuestionInGameDTO;
import com.pw.quizwhizz.dto.game.QuestionInGameKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionInGameRepository extends JpaRepository<QuestionInGameDTO, QuestionInGameKey> {

    List<QuestionInGameDTO> findAllById_GameId(Long id);

}
