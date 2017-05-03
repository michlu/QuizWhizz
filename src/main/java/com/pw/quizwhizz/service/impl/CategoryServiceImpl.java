package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.repository.game.CategoryRepository;
import com.pw.quizwhizz.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Override
    public Category findByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void addCategory(Category category){
        if(category.getUrlImage().equals(null) || category.getUrlImage().equals(""))
            category.setUrlImage("/resources/images/category_default.png");
        categoryRepository.save(category);
    }

    @Override
    public void addCategoryWithImage(Category category, MultipartFile file, String saveDirectory) throws IOException {

        String fileNameWithExtension = "category_" + category.getName().toLowerCase().replace(' ', '_') + "." + file.getOriginalFilename().split("\\.")[1];
        byte[] bytes = file.getBytes();
        category.setUrlImage("/resources/images/" + fileNameWithExtension);
        Path path = Paths.get(saveDirectory + fileNameWithExtension);
        Files.write(path, bytes);
        categoryRepository.save(category);
    }

    @Transactional
    @Modifying
    @Override
    public void updateCategoryWithImage(Category updateCategory, MultipartFile file, String saveDirectory) throws IOException {
        String fileNameWithExtension = "category_" + updateCategory.getName().toLowerCase().replace(' ', '_') + "." + file.getOriginalFilename().split("\\.")[1];
        byte[] bytes = file.getBytes();
        updateCategory.setUrlImage("/resources/images/" + fileNameWithExtension);
        Path path = Paths.get(saveDirectory + fileNameWithExtension);
        Files.write(path, bytes);
        categoryRepository.saveAndFlush(updateCategory);
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
