package com.pw.quizwhizz.annotation;

import com.pw.quizwhizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Walidator Emaila, sprawdza czy dany email jest unikatowy w bazie danych. Nie pozwala na dodanie dwoch takich samych emaili.
 * @author Michał Nowiński
 * @see ConstraintValidator
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