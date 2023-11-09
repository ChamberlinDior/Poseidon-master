package com.openclassroomsprojet.poseidon.repositories;

import com.openclassroomsprojet.poseidon.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Gives access to the JPA CRUD methods on the Trade entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}