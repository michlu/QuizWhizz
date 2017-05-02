package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.game.GameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameDTO, Long> {
    GameDTO findById(Long id);
}
