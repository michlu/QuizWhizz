package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.dto.game.CategoryDTO;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        List<CategoryDTO> categoriesDTO = categoryRepository.findAll();
        List<Category> categories = new ArrayList<>();
        for (CategoryDTO categoryDTO : categoriesDTO) {
            Category category = convertToCategory(categoryDTO);
            categories.add(category);
        }
        return categories;
    }

    @Override
    public Category findById(Long id) {
        CategoryDTO categoryDTO =  categoryRepository.findOne(id);
        Category category = convertToCategory(categoryDTO);
        return category;
    }

    @Override
    public Category findByName(String categoryName) {
        CategoryDTO categoryDTO =  categoryRepository.findByName(categoryName);
        Category category = convertToCategory(categoryDTO);
        return category;
    }

    @Override
    public CategoryDTO findCategoryDTOById(long id) {
        return categoryRepository.findOne(id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void addCategory(Category category){
        if(category.getUrlImage().equals(null) || category.getUrlImage().equals(""))
            category.setUrlImage("/resources/images/category_default.png");

        saveAsCategoryDTO(category);
    }

    @Transactional
    @Override
    public void addCategoryWithImage(Category category, MultipartFile file, String saveDirectory) throws IOException {
        String fileNameWithExtension = "category_" + category.getName().toLowerCase().replace(' ', '_') + "." + file.getOriginalFilename().split("\\.")[1];
        byte[] bytes = file.getBytes();
        category.setUrlImage("/resources/images/" + fileNameWithExtension);
        Path path = Paths.get(saveDirectory + fileNameWithExtension);
        Files.write(path, bytes);
        saveAsCategoryDTO(category);
    }

    @Transactional
    @Modifying
    @Override
    public void updateCategoryWithImage(Category updatedCategory, MultipartFile file, String saveDirectory) throws IOException {
        String fileNameWithExtension = "category_" + updatedCategory.getName().toLowerCase().replace(' ', '_') + "." + file.getOriginalFilename().split("\\.")[1];
        byte[] bytes = file.getBytes();
        updatedCategory.setUrlImage("/resources/images/" + fileNameWithExtension);
        Path path = Paths.get(saveDirectory + fileNameWithExtension);
        Files.write(path, bytes);

        CategoryDTO updatedCategoryDTO = getCategoryDTO(updatedCategory);
        categoryRepository.saveAndFlush(updatedCategoryDTO);
    }

    @Transactional
    @Modifying
    @Override
    public void updateCategory(Category updatedCategory){
        CategoryDTO updatedCategoryDTO = getCategoryDTO(updatedCategory);
        categoryRepository.saveAndFlush(updatedCategoryDTO);
    }

    @Transactional
    @Modifying
    @Override
    public void updateCategoryById(Long id, String name, String description, String urlImage){
        CategoryDTO updatedCategoryDTO = categoryRepository.findOne(id);
        updatedCategoryDTO.setName(name);
        updatedCategoryDTO.setDescription(description);
        updatedCategoryDTO.setUrlImage(urlImage);
        categoryRepository.saveAndFlush(updatedCategoryDTO);
    }

    private CategoryDTO getCategoryDTO(Category updatedCategory) {
        CategoryDTO updatedCategoryDTO = categoryRepository.findOne(updatedCategory.getId());
        updatedCategoryDTO.setName(updatedCategory.getName());
        updatedCategoryDTO.setDescription(updatedCategory.getDescription());
        updatedCategoryDTO.setUrlImage(updatedCategory.getUrlImage());
        return updatedCategoryDTO;
    }

    private Category convertToCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setUrlImage(categoryDTO.getUrlImage());
        return category;
    }

    private void saveAsCategoryDTO(Category category) {
        CategoryDTO updatedCategoryDTO = new CategoryDTO();
        updatedCategoryDTO.setName(category.getName());
        updatedCategoryDTO.setDescription(category.getDescription());
        updatedCategoryDTO.setUrlImage(category.getUrlImage());
        CategoryDTO categoryDTO = updatedCategoryDTO;
        categoryRepository.save(categoryDTO);
        category.setId(categoryDTO.getId());
    }
}
