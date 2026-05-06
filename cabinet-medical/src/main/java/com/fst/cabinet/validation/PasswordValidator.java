package com.fst.cabinet.validation;
//validation

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

// ← Doit implémenter ConstraintValidator<ValidPassword, String>
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final Pattern PATTERN = Pattern.compile(
        "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#+\\-_]).{8,}$"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;
        return PATTERN.matcher(value).matches();
    }
}