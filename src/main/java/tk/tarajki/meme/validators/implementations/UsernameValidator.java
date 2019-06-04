package tk.tarajki.meme.validators.implementations;


import tk.tarajki.meme.validators.annotations.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.length() > 32 || value.length() < 3) {
            return false;
        }

        if (!value.chars().allMatch(Character::isLetterOrDigit)) {
            return false;
        }

        return true;
    }

    @Override
    public void initialize(Username constraintAnnotation) {

    }
}
