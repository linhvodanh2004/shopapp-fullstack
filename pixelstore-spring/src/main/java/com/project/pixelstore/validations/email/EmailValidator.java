package com.project.pixelstore.validations.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return false;
        }
        email = email.trim();
        if (!pattern.matcher(email).matches()) {
            return false;
        }
        if (email.length() > 254) { // RFC 5321 limit
            return false;
        }
        String[] parts = email.split("@");
        if (parts[0].length() > 64) { // RFC 5321 limit
            return false;
        }
        if (email.contains("..")) {
            return false;
        }
        return true;
    }
}
