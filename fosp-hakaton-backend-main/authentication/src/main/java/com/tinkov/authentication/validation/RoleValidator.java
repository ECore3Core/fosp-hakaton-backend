package com.tinkov.authentication.validation;

import com.tinkov.authentication.entity.Role;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, Role> {

    @Override
    public boolean isValid(Role value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value == Role.ROLE_USER || value == Role.ROLE_COMPANY;
    }
}
