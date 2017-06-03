package com.pw.quizwhizz.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adnotacja @UniqueCategory powiazana z UniqueCategoryValidator
 * @author Michał Nowiński
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueCategoryValidator.class})
public @interface UniqueCategory {

    String message() default "{org.hibernate.validator.constraints.Category.isused}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
