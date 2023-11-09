package com.openclassroomsprojet.poseidon.service;

import com.openclassroomsprojet.poseidon.domain.BidList;
import java.util.List;
import java.util.Optional;

/**
 * @author jonathan GOUVEIA
 * @version 1.0
 */
public interface IBidListService {

    List<BidList> findAllBidList();

    Optional<BidList> findBidListById(int id);

    void deleteBidListById(int id);

    void saveBidList(BidList bidList);
}