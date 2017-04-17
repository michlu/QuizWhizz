package com.pw.quizwhizz.annotation;

import com.pw.quizwhizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator sprawdza czy dany mail istnieje juz w bazie danych.
 */
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueEmail uniqueEmail) {
    }
    @Override
    public boolean isValid(String mail, ConstraintValidatorContext constraintValidatorContext) {
        if (userService == null)
            return true;
        return userService.findByEmail(mail) == null;
    }
}