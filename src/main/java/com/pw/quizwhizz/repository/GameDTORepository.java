package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.entity.GameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface GameDTORepository extends JpaRepository<GameDTO, Long> {
    GameDTO findById(Long id);
}
