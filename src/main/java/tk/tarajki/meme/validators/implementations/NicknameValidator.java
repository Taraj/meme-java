package tk.tarajki.meme.validators.implementations;

import tk.tarajki.meme.validators.annotations.Nickname;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NicknameValidator implements ConstraintValidator<Nickname, String> {
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
    public void initialize(Nickname constraintAnnotation) {

    }
}
