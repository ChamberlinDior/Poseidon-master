package com.openclassroomsprojet.poseidon.service;

import com.openclassroomsprojet.poseidon.domain.User;
import java.util.List;
import java.util.Optional;

/**
 * @author jonathan GOUVEIA
 * @version 1.0
 */
public interface IUserService {

    List<User> findAllUsers();

    Optional<User> findUserById(int id);

    void deleteUserById(int id);

    void saveUser(User user);
}