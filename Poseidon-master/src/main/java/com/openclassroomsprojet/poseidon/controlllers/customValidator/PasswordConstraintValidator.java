package com.openclassroomsprojet.poseidon.controlllers.customValidator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.*;

/**
 * This class validate candidate passwords against a configurable rule set. Use Passay library
 *
 * @author jonathan GOUVEIA
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
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 99),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        ));
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);
        String messageTemplate = messages.stream()
                .collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}