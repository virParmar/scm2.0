package com.scm.scm20.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nimbusds.jose.Payload;

import jakarta.validation.Constraint;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= FileValidator.class)
public @interface ValidFile {

    String message() default "Invalid file";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};

}
