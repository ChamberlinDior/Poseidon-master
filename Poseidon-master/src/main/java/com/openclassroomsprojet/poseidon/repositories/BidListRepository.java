package com.openclassroomsprojet.poseidon.repositories;

import com.openclassroomsprojet.poseidon.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Gives access to  the JPA CRUD methods on the BidList entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

    Optional<BidList> findBidListByBidListId(int id);
}