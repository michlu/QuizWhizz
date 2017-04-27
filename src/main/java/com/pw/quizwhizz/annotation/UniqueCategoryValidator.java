package com.pw.quizwhizz.annotation;

import com.pw.quizwhizz.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author michlu
 * @sience 26.04.2017
 */
public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, String> {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void initialize(UniqueCategory uniqueCategory) {

    }

    @Override
    public boolean isValid(String categoryName, ConstraintValidatorContext constraintValidatorContext) {
        if (categoryService == null)
            return true;
        return categoryService.findByName(categoryName) == null;
    }
}
