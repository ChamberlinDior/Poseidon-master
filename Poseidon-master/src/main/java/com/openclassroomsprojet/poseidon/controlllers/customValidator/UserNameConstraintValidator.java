package com.openclassroomsprojet.poseidon.controlllers.customValidator;

import com.openclassroomsprojet.poseidon.domain.User;
import com.openclassroomsprojet.poseidon.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Cette classe valide le nom d'utilisateur candidat en vérifiant dans la base de données s'il existe déjà
 * (This class validate candidate username by checking in the database if it already exists)
 *
 * @author chamberlin dior
 * @version 1.0
 */
@Slf4j // Utiliser l'annotation Slf4j pour créer un logger
@Component // Indiquer que cette classe est un composant géré par Spring
public class UserNameConstraintValidator implements ConstraintValidator<ValidUserName, String> {

    @Autowired // Injecter les dépendances automatiquement
    private UserRepository userRepository; // Déclarer le dépôt d'utilisateurs

    /**
     * Cette méthode contient un appel au dépôt d'utilisateurs pour vérifier si le nom d'utilisateur existe déjà ou non
     * @param username La chaîne à vérifier
     * @param context Fournit des données contextuelles et des opérations lors de l'application d'un
     *validateur de contrainte donné
     * @return La réponse au test de validation
     */
    @Override // Redéfinir la méthode de l'interface
    public boolean isValid(String username, ConstraintValidatorContext context) {
        User user = userRepository.findUserByUsername(username); // Chercher un utilisateur par son nom d'utilisateur
        if (user == null) { // Si aucun utilisateur n'est trouvé
            log.info("UserNameConstraintValidator: Username is free"); // Afficher un message d'information
            return true; // Renvoyer vrai
        }
        log.info("UserNameConstraintValidator: Username is already use"); // Afficher un message d'information
        return false; // Renvoyer faux
    }
}