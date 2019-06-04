package tk.tarajki.meme.validators.implementations;

import tk.tarajki.meme.validators.annotations.Comment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CommentValidator implements ConstraintValidator<Comment, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value.length() <= 2048;
    }

    @Override
    public void initialize(Comment constraintAnnotation) {

    }
}
