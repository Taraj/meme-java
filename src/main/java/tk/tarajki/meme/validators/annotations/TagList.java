package tk.tarajki.meme.validators.annotations;

import tk.tarajki.meme.validators.implementations.TagListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TagListValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TagList {
    String message() default "Invalid tag list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
