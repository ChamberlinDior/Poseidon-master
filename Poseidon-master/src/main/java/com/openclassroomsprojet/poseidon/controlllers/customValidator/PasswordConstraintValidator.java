package com.openclassroomsprojet.poseidon.controlllers.customValidator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.*;
/**
 * Cette classe valide les mots de passe candidats selon un ensemble de règles configurable. Utilise la bibliothèque Passay
 * (This class validate candidate passwords against a configurable rule set. Use Passay library)
 *
 * @author chamberlin dior
 * @version 1.0
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
    }

    /**
     * Allows to configure the rules and to build, if necessary, an error message
     * LengthRule({min}, {max}): range of character
     * CharacterRule(EnglishCharacterData.UpperCase, {number}): minimum number of upper-case character
     * CharacterRule(EnglishCharacterData.LowerCase, {number}): minimum number of lower-case character
     * CharacterRule(EnglishCharacterData.Digit, {number}): minimum number of digit character
     * CharacterRule(EnglishCharacterData.Special, {number}): minimum number of special character
     * WhitespaceRule(): no whitespace
     *
     * @param password The string to check
     * @param context  Provides contextual data and operation when applying a given constraint validator
     * @return boolean. The answer to the validation test
     */
    @Override // Redéfinir la méthode de l'interface
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 99), // Créer une règle de longueur entre 8 et 99 caractères
                new CharacterRule(EnglishCharacterData.UpperCase, 1), // Créer une règle de caractère majuscule avec un minimum de 1
                new CharacterRule(EnglishCharacterData.LowerCase, 1), // Créer une règle de caractère minuscule avec un minimum de 1
                new CharacterRule(EnglishCharacterData.Digit, 1), // Créer une règle de caractère numérique avec un minimum de 1
                new CharacterRule(EnglishCharacterData.Special, 1), // Créer une règle de caractère spécial avec un minimum de 1
                new WhitespaceRule() // Créer une règle sans espace
        ));
        RuleResult result = validator.validate(new PasswordData(password)); // Valider le mot de passe avec le validateur
        if (result.isValid()) { // Si le mot de passe est valide
            return true; // Renvoyer vrai
        }
        List<String> messages = validator.getMessages(result); // Sinon, récupérer la liste des messages d'erreur
        String messageTemplate = messages.stream()
                .collect(Collectors.joining(",")); // Concaténer les messages d'erreur avec une virgule
        context.buildConstraintViolationWithTemplate(messageTemplate) // Construire une violation de contrainte avec le message d'erreur
                .addConstraintViolation() // Ajouter la violation de contrainte
                .disableDefaultConstraintViolation(); // Désactiver la violation de contrainte par défaut
        return false; // Renvoyer faux
    }
}