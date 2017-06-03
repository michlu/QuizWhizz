package com.pw.quizwhizz.service;

import com.pw.quizwhizz.entity.game.CategoryEntity;
import com.pw.quizwhizz.model.game.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Abstrakcyjna warstwa serwisu
 * @author Michał Nowiński, Karolina Prusaczyk
 */
public interface CategoryService {
    List<Category> findAll();
    void addCategory(Category category);
    Category findById(Long l);
    Category findByName(String categoryName);
    CategoryEntity findCategoryEntityById(long id);
    void deleteById(Long Id);
    void updateCategoryById(Long Id, String name, String description, String urlImage);
    void updateCategory(Category category);
    void addCategoryWithImage(Category category, MultipartFile file, String saveDirectory) throws IOException;
    void updateCategoryWithImage(Category category, MultipartFile file, String saveDirectory) throws IOException;
}
