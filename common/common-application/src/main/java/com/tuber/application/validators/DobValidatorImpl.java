package com.tuber.application.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class DobValidatorImpl implements ConstraintValidator<ValidDob, LocalDate> {
    @Override
    public void initialize(ValidDob constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return false;
        }

        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();

        if (age < 1) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The age must larger or equal 1").addConstraintViolation();
            return false;
        }

        if (age > 120) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The age must smaller or equal 120").addConstraintViolation();
            return false;
        }

        return true;
    }
}
