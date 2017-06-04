package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.CategoryEntity;
import com.pw.quizwhizz.entity.game.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repozytorium udostepnia encje QuestionEntity
 * @author Michał Nowiński
 * @see JpaRepository
 */
@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    void deleteById(Long id);
    List<QuestionEntity> findAllByCategory_Id(Long categoryId);
    List<QuestionEntity> findAllByCategory(CategoryEntity categoryEntity);

    /** @return zwraca ilosc wszystkich zapisanych pytan */
    @Query(value = "SELECT count(q) FROM QuestionEntity q")
    int countAll();
}

