package com.openclassroomsprojet.poseidon.repositories;

import com.openclassroomsprojet.poseidon.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Gives access to the JPA CRUD methods on the Rating entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
}