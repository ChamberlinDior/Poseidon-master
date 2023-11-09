package com.openclassroomsprojet.poseidon.service.impl;

import com.openclassroomsprojet.poseidon.domain.Rating;
import com.openclassroomsprojet.poseidon.repositories.RatingRepository;
import com.openclassroomsprojet.poseidon.service.IRatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * This class contains methods that allow performing CRUD actions by calling the rating repository
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Slf4j
@Service
public class RatingServiceImpl implements IRatingService {

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Allows to search all the Ratings contained in the database
     *
     * @return A list of Rating
     */
    @Override
    public List<Rating> findAllRating() {
        log.info("Call service method: findAllRating()");
        return ratingRepository.findAll();
    }

    /**
     * Searches for a Rating object from its identifier
     *
     * @param id The identifier of the object to find
     * @return An optional which may or may not contain the requested object
     */
    @Override
    public Optional<Rating> findRatingById(int id) {
        log.info("Call service method: findRatingById(int id)");
        return ratingRepository.findById(id);
    }

    /**
     * Delete a Rating
     *
     * @param id The identifier of the object to delete
     */
    @Override
    public void deleteRatingById(int id) {
        log.info("Call service method: deleteRatingById(int id)");
        ratingRepository.deleteById(id);
    }

    /**
     * Save a BidList
     *
     * @param rating Object to be saved
     */
    @Override
    public void saveRating(Rating rating) {
        log.info("Call service method: saveRating(Rating rating)");
        ratingRepository.save(rating);
    }
}