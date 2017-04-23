package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.entity.Category;
import com.pw.quizwhizz.repository.CategoryRepository;
import com.pw.quizwhizz.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void addCategory(Category category){
        if(category.getUrlImage().equals(null) || category.getUrlImage().equals(""))
            category.setUrlImage("/resources/images/default.png");
        categoryRepository.save(category);
    }

    @Transactional
    @Modifying
    @Override
    public void updateCategoryById(Long id, String name, String description, String urlImage){
        categoryRepository.updateCategoryById(id, name, description, urlImage);
    }

    @Transactional
    @Modifying
    @Override
    public void updateCategory(Category updateCategory){
        categoryRepository.saveAndFlush(updateCategory);
    }


}
