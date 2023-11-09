package com.openclassroomsprojet.poseidon.controlllers.customValidator;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Interface for custom validation rules
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Documented
@Constraint(validatedBy = UserNameConstraintValidator.class)
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidUserName {

    String message() default "This username is already used";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}