package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.entity.game.CategoryEntity;
import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.repository.game.CategoryRepository;
import com.pw.quizwhizz.service.CategoryService;
import com.pw.quizwhizz.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Serwis domenowy udostepniajacy funkcjonalnosci dla domeny Category
 * @author Michał Nowiński, Karolina Prusaczyk
 * @see CategoryService
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ImageUtil imageUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ImageUtil imageUtil) {
        this.categoryRepository = categoryRepository;
        this.imageUtil = imageUtil;
    }

    @Override
    public List<Category> findAll() {
        List<CategoryEntity> categoriesEntity = categoryRepository.findAll();
        List<Category> categories = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoriesEntity) {
            Category category = convertToCategory(categoryEntity);
            categories.add(category);
        }
        return categories;
    }

    @Override
    public Category findById(Long id) {
        CategoryEntity categoryEntity =  categoryRepository.findOne(id);
        Category category = convertToCategory(categoryEntity);
        return category;
    }

    @Override
    public Category findByName(String categoryName) {
        CategoryEntity categoryEntity =  categoryRepository.findByName(categoryName);
        Category category = convertToCategory(categoryEntity);
        return category;
    }

    @Override
    public CategoryEntity findCategoryEntityById(long id) {
        return categoryRepository.findOne(id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    /**
     * Dodaje Kategorie z domyslnym obrazem
     * @param category przyjmuje obiekt Category z formularza
     */
    @Transactional
    @Override
    public void addCategory(Category category){
        if(category.getUrlImage().equals(null) || category.getUrlImage().equals(""))
            category.setUrlImage("/resources/images/category_default.png");
        saveAsCategoryEntity(category);
    }

    /**
     * Dodaje Kategorie z obrazem przekazanym w fomularzu
     * @param category przyjmuje obiekt Category z formularza
     * @param file plik obrazu
     * @param saveDirectory sciezka do zapisu pliku obrazu
     * @throws IOException wymagane przez Files
     */
    @Transactional
    @Override
    public void addCategoryWithImage(Category category, MultipartFile file, String saveDirectory) throws IOException {
        String fileNameWithExtension = "category_" + category.getName().toLowerCase().replace(' ', '_') + "." + file.getOriginalFilename().split("\\.")[1];
        BufferedImage resizedImage = imageUtil.resizeImage(file.getBytes(), 200, 200);
        category.setUrlImage("/resources/images/" + fileNameWithExtension);
        Path path = Paths.get(saveDirectory + fileNameWithExtension);
        Files.write(path, imageUtil.imageToByte(resizedImage));
        saveAsCategoryEntity(category);
    }

    /**
     * Aktualizuje Kategorie z obrazem przekzanym w formularzu edcyji kategorii
     * @param updatedCategory przyjmuje obiekt Category z formularza
     * @param file plik obrazu
     * @param saveDirectory sciezka do zapisu pliku obrazu
     * @throws IOException wymagane przez Files
     */
    @Transactional
    @Modifying
    @Override
    public void updateCategoryWithImage(Category updatedCategory, MultipartFile file, String saveDirectory) throws IOException {
        String fileNameWithExtension = "category_" + updatedCategory.getName().toLowerCase().replace(' ', '_') + "." + file.getOriginalFilename().split("\\.")[1];
        BufferedImage resizedImage = imageUtil.resizeImage(file.getBytes(), 200, 200);
        updatedCategory.setUrlImage("/resources/images/" + fileNameWithExtension);
        Path path = Paths.get(saveDirectory + fileNameWithExtension);
        Files.write(path, imageUtil.imageToByte(resizedImage));

        CategoryEntity updatedCategoryEntity = getCategoryEntity(updatedCategory);
        categoryRepository.saveAndFlush(updatedCategoryEntity);
    }

    /**
     * Aktualizuje Kategorie bez zmiany obrazu
     * @param updatedCategory przyjmuje obiekt Category z formularza
     */
    @Transactional
    @Modifying
    @Override
    public void updateCategory(Category updatedCategory){
        CategoryEntity updatedCategoryEntity = getCategoryEntity(updatedCategory);
        categoryRepository.saveAndFlush(updatedCategoryEntity);
    }

    /**
     * Aktualizuje Kategorie
     * @deprecated
     */
    @Transactional
    @Modifying
    @Override
    public void updateCategoryById(Long id, String name, String description, String urlImage){
        CategoryEntity updatedCategoryEntity = categoryRepository.findOne(id);
        updatedCategoryEntity.setName(name);
        updatedCategoryEntity.setDescription(description);
        updatedCategoryEntity.setUrlImage(urlImage);
        categoryRepository.saveAndFlush(updatedCategoryEntity);
    }

    private CategoryEntity getCategoryEntity(Category updatedCategory) {
        CategoryEntity updatedCategoryEntity = categoryRepository.findOne(updatedCategory.getId());
        updatedCategoryEntity.setName(updatedCategory.getName());
        updatedCategoryEntity.setDescription(updatedCategory.getDescription());
        updatedCategoryEntity.setUrlImage(updatedCategory.getUrlImage());
        return updatedCategoryEntity;
    }

    private Category convertToCategory(CategoryEntity categoryEntity) {
        Category category = new Category();
        category.setId(categoryEntity.getId());
        category.setName(categoryEntity.getName());
        category.setDescription(categoryEntity.getDescription());
        category.setUrlImage(categoryEntity.getUrlImage());
        return category;
    }

    private void saveAsCategoryEntity(Category category) {
        CategoryEntity updatedCategoryEntity = new CategoryEntity();
        updatedCategoryEntity.setName(category.getName());
        updatedCategoryEntity.setDescription(category.getDescription());
        updatedCategoryEntity.setUrlImage(category.getUrlImage());
        CategoryEntity categoryEntity = updatedCategoryEntity;
        categoryRepository.save(categoryEntity);
        category.setId(categoryEntity.getId());
    }
}
