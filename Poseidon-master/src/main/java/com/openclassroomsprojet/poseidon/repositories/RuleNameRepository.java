package com.openclassroomsprojet.poseidon.repositories;

import com.openclassroomsprojet.poseidon.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Gives access to the JPA CRUD methods on the RuleName entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}