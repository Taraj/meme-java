package tk.tarajki.meme.validators.implementations;

import tk.tarajki.meme.validators.annotations.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value.length() <= 32 && value.length() >= 3;
    }

    @Override
    public void initialize(Password constraintAnnotation) {

    }
}
