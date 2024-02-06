package com.openclassroomsprojet.poseidon.service.impl;

import com.openclassroomsprojet.poseidon.domain.User;
import com.openclassroomsprojet.poseidon.repositories.UserRepository;
import com.openclassroomsprojet.poseidon.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Cette classe contient des méthodes permettant d'effectuer des opérations CRUD en appelant le référentiel d'utilisateurs.
 *
 * @author chamberlin dior
 * @version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Permet de rechercher tous les utilisateurs contenus dans la base de données.
     *
     * @return Une liste d'utilisateurs.
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Recherche un objet Utilisateur à partir de son identifiant.
     *
     * @param id L'identifiant de l'objet à trouver.
     * @return Un optionnel qui peut contenir ou non l'objet demandé.
     */
    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    /**
     * Supprime un utilisateur.
     *
     * @param id L'identifiant de l'objet à supprimer.
     */
    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    /**
     * Enregistre un utilisateur.
     *
     * @param user Objet à enregistrer.
     */
    @Override
    public void saveUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}