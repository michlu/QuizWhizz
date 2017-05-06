package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.dto.game.CategoryDTO;
import com.pw.quizwhizz.model.game.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDTO, Long> {
    CategoryDTO findByName(String categoryName);
    void deleteById(Long id);
}
