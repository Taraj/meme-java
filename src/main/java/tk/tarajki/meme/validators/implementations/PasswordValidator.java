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

        if (value.length() > 32 || value.length() < 3) {
            return false;
        }

        return true;
    }

    @Override
    public void initialize(Password constraintAnnotation) {

    }
}
