package tk.tarajki.meme.validators.implementations;

import tk.tarajki.meme.validators.annotations.TagList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class TagListValidator implements ConstraintValidator<TagList, List<String>> {
    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.size() > 3 || value.size() < 1) {
            return false;
        }

        return value.stream().allMatch(it -> it.length() > 2 && it.length() < 20);
    }

    @Override
    public void initialize(TagList constraintAnnotation) {

    }
}
