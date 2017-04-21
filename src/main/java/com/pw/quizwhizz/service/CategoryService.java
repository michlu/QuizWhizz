package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.gameLogic.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    void addCategory(Category category);
    Category findById(Long l);
    void deleteById(Long Id);
    void updateCategoryById(Long Id, String name, String description, String urlImage);
    void updateCategory(Category category);
}
