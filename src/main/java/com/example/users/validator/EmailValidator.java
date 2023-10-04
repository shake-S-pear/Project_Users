package com.example.users.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.regex.Pattern;

@Configurable
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private Pattern pattern;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        pattern = Pattern.compile(EMAIL_PATTERN);;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (email == null || "".equals(email.trim())) return false;
        return pattern.matcher(email).matches();
    }

}
