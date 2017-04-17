package com.pw.quizwhizz.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueEmailValidator.class})
public @interface UniqueEmail {

    String message() default "{org.hibernate.validator.constraints.Email.isused}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

