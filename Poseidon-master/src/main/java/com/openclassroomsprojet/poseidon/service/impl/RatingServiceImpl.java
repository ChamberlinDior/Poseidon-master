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
 * Cette classe contient des méthodes permettant d'effectuer des opérations CRUD en appelant le référentiel de notes.
 *
 * @author chamberlin dior
 * @version 1.0
 */
@Slf4j
@Service
public class RatingServiceImpl implements IRatingService {

    @Autowired
    private RatingRepository ratingRepository;
    /**
     * Permet de rechercher toutes les notes contenues dans la base de données.
     *
     * @return Une liste de notes.
     */
    @Override
    public List<Rating> findAllRating() {
        log.info("Call service method: findAllRating()");
        return ratingRepository.findAll();
    }

    /**
     * Recherche un objet Note à partir de son identifiant.
     *
     * @param id L'identifiant de l'objet à trouver.
     * @return Un optionnel qui peut contenir ou non l'objet demandé.
     */
    @Override
    public Optional<Rating> findRatingById(int id) {
        log.info("Call service method: findRatingById(int id)");
        return ratingRepository.findById(id);
    }

    /**
     * Supprime une note.
     *
     * @param id L'identifiant de l'objet à supprimer.
     */
    @Override
    public void deleteRatingById(int id) {
        log.info("Call service method: deleteRatingById(int id)");
        ratingRepository.deleteById(id);
    }

    /**
     * Enregistre une note.
     *
     * @param note Objet à enregistrer.
     */
    @Override
    public void saveRating(Rating rating) {
        log.info("Call service method: saveRating(Rating rating)");
        ratingRepository.save(rating);
    }
}