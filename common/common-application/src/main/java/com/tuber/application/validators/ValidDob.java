package com.tuber.application.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = DobValidatorImpl.class)
public @interface ValidDob {
    String message() default "Invalid day of birth";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
