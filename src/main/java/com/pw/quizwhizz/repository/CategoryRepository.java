package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findById(Long id);
    void deleteById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.name = ?2, c.description = ?3, c.urlImage = ?4 WHERE c.id = ?1")
    void updateCategoryById(Long id, String name, String description, String urlImage);

}
