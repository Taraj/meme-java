package tk.tarajki.meme.validators.annotations;

import tk.tarajki.meme.validators.implementations.PostTitleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PostTitleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PostTitle {
    String message() default "Invalid post title";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
