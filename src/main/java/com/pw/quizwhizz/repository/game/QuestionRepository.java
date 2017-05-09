package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.CategoryEntity;
import com.pw.quizwhizz.entity.game.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    void deleteById(Long id);
    List<QuestionEntity> findAllByCategory_Id(Long categoryId);
    List<QuestionEntity> findAllByCategory(CategoryEntity categoryEntity);
}

