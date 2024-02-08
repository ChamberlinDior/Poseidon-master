package com.openclassroomsprojet.poseidon.controlllers.customValidator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.*;
/**
 * Cette classe valide les mots de passe candidats selon un ensemble de règles configurable. Utilise la bibliothèque Passay
 *
 * @author chamberlin dior
 * @version 1.0
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
    }

    /**
     * Permet de configurer les règles et de construire, si nécessaire, un message d'erreur
     * LengthRule({min}, {max}): plage de caractères
     * CharacterRule(EnglishCharacterData.UpperCase, {number}): nombre minimum de caractères majuscules
     * CharacterRule(EnglishCharacterData.LowerCase, {number}): nombre minimum de caractères minuscules
     * CharacterRule(EnglishCharacterData.Digit, {number}): nombre minimum de caractères numériques
     * CharacterRule(EnglishCharacterData.Special, {number}): nombre minimum de caractères spéciaux
     * WhitespaceRule(): pas d'espace
     *
     * @param password La chaîne à vérifier
     * @param context Fournit des données contextuelles et des opérations lors de l'application d'un validateur de contrainte donné
     * @return boolean. La réponse au test de validation
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