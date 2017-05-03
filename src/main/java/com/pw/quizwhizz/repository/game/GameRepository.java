package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.dto.game.GameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameDTO, Long> {
}
