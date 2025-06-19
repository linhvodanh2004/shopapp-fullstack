package com.project.pixelstore.validations.password;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Invalid password: Password must be 8-20 characters long and contain at least one uppercase letter, one lowercase letter, one number and one special character";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
