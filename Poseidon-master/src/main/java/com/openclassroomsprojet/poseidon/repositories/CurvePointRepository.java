package com.openclassroomsprojet.poseidon.repositories;

import com.openclassroomsprojet.poseidon.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Gives access to the JPA CRUD methods on the CurvePoint entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
}