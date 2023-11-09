package com.openclassroomsprojet.poseidon.controlllers.customValidator;

import com.openclassroomsprojet.poseidon.domain.User;
import com.openclassroomsprojet.poseidon.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * This class validate candidate username by checking in the database if it already exists
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Slf4j
@Component
public class UserNameConstraintValidator implements ConstraintValidator<ValidUserName, String> {

    @Autowired
    private UserRepository userRepository;

    /**
     * This method contain a user repository call to check if the username already exists or not
     *
     * @param username The string to check
     * @param context Provides contextual data and operation when applying a given constraint validator
     * @return The answer to the validation test
     */
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            log.info("UserNameConstraintValidator: Username is free");
            return true;
        }
        log.info("UserNameConstraintValidator: Username is already use");
        return false;
    }
}