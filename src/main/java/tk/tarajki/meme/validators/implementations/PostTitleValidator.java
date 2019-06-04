package tk.tarajki.meme.validators.implementations;

import tk.tarajki.meme.validators.annotations.Password;
import tk.tarajki.meme.validators.annotations.PostTitle;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostTitleValidator implements ConstraintValidator<PostTitle, String> {
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
    public void initialize(PostTitle constraintAnnotation) {

    }
}
