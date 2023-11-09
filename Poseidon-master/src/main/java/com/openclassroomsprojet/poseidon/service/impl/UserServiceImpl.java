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
 * This class contains methods that allow performing CRUD actions by calling the user repository
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Allows to search all the Users contained in the database
     *
     * @return A list of User
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Searches for a User object from its identifier
     *
     * @param id The identifier of the object to find
     * @return An optional which may or may not contain the requested object
     */
    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    /**
     * Delete a User
     *
     * @param id The identifier of the object to delete
     */
    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    /**
     * Save a User
     *
     * @param user Object to be saved
     */
    @Override
    public void saveUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}