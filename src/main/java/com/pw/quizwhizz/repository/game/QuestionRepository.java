package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.dto.game.CategoryDTO;
import com.pw.quizwhizz.dto.game.QuestionDTO;
import com.pw.quizwhizz.model.game.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionDTO, Long> {
    void deleteById(Long id);
    List<QuestionDTO> findAllByCategory_Id(Long categoryId);
    List<QuestionDTO> findAllByCategory(CategoryDTO categoryDTO);
}

