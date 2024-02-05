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
 * Interface pour les règles de validation personnalisées
 * @author chamberlin dior
 * @version 1.0
 */
@Documented // Indiquer que cette annotation doit être documentée par javadoc
@Constraint(validatedBy = UserNameConstraintValidator.class) // Indiquer que cette annotation est une contrainte validée par la classe UserNameConstraintValidator
@Target({FIELD, ANNOTATION_TYPE}) // Indiquer que cette annotation peut être appliquée sur un champ ou une autre annotation
@Retention(RUNTIME) // Indiquer que cette annotation doit être conservée à l'exécution
public @interface ValidUserName { // Déclarer une annotation publique nommée ValidUserName

    String message() default "This username is already used"; // Définir l'attribut message avec une valeur par défaut
    // "This username is already used"

    Class<?>[] groups() default {}; // Définir l'attribut groups avec une valeur par défaut vide

    Class<? extends Payload>[] payload() default {}; // Définir l'attribut payload avec une valeur par défaut vide
}
