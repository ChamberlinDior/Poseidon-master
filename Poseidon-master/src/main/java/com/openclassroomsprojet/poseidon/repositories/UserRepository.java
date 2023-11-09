package com.openclassroomsprojet.poseidon.repositories;

import com.openclassroomsprojet.poseidon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Gives access to the JPA CRUD methods on the User entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
}