package ru.help.wanted.project.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Constraint(validatedBy = PasswordConstraintValidator.class)
@Retention(RUNTIME)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
public @interface ValidPassword {
    String message() default "Invalid Password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
