package com.tuber.identity.service.domain.validators;

import com.tuber.domain.valueobject.enums.UserPermission;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class PermissionValidatorImpl implements ConstraintValidator<ValidPermission, String> {
    @Override
    public void initialize(ValidPermission constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    private String buildMessage() {
        String message = "The permission name must be one of these value: [ %s ]";
        String permissionNames = String.join(", ",
                Arrays.stream(UserPermission.values())
                        .map(Enum::name)
                        .toArray(String[]::new)
        );

        return String.format(message, permissionNames);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            UserPermission.valueOf(s);
            return true;
        } catch (IllegalArgumentException e) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(buildMessage()).addConstraintViolation();
            return false;
        }
    }
}
