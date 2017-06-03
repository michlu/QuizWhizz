package com.pw.quizwhizz.annotation;

import com.pw.quizwhizz.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Walidator Kategorii, sprawdza czy dana kategoria jest unikatowa w bazie danych. Nie pozwala na dodanie dwoch takich samych kategorii.
 * @author Michał Nowiński
 * @see ConstraintValidator
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
