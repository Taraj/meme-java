package tk.tarajki.meme.validators.implementations;

import tk.tarajki.meme.validators.annotations.PostTitle;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostTitleValidator implements ConstraintValidator<PostTitle, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value.length() <= 32 && value.length() >= 3;
    }

    @Override
    public void initialize(PostTitle constraintAnnotation) {

    }
}
