package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.entity.game.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium udostepnia encje CategoryEntity
 * @author Michał Nowiński
 * @see JpaRepository
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findByName(String categoryName);
    void deleteById(Long id);
}
