package tk.tarajki.meme.validators.annotations;

import tk.tarajki.meme.validators.implementations.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {
    String message() default "Invalid comment";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
