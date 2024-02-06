package com.openclassroomsprojet.poseidon.service.impl;

import com.openclassroomsprojet.poseidon.domain.CurvePoint;
import com.openclassroomsprojet.poseidon.repositories.CurvePointRepository;
import com.openclassroomsprojet.poseidon.service.ICurvePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Cette classe contient des méthodes permettant d'effectuer des opérations CRUD en appelant le référentiel de points de courbe.
 *
 * @author chamberlin dior
 * @version 1.0
 */
@Slf4j
@Service
public class CurvePointServiceImpl implements ICurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Permet de rechercher tous les points de courbe contenus dans la base de données.
     *
     * @return Une liste de points de courbe.
     */
    @Override
    public List<CurvePoint> findAllCurvePoint() {
        log.info("Call service method: findAllCurvePoint()");
        return curvePointRepository.findAll();
    }

    /**
     * Recherche un objet PointCourbe à partir de son identifiant.
     *
     * @param id L'identifiant de l'objet à trouver.
     * @return Un optionnel qui peut contenir ou non l'objet demandé.
     */
    @Override
    public Optional<CurvePoint> findCurvePointById(int id) {
        log.info("Call service method: findCurvePointById(int id)");
        return curvePointRepository.findById(id);
    }

    /**
     * Supprime un point de courbe.
     *
     * @param id L'identifiant de l'objet à supprimer.
     */
    @Override
    public void deleteCurvePointById(int id) {
        log.info("Call service method: deleteCurvePointById(int id)");
        curvePointRepository.deleteById(id);
    }

    /**
     * Enregistre un point de courbe.
     *
     * @param pointCourbe Objet à enregistrer.
     */
    @Override
    public void saveCurvePoint(CurvePoint curvePoint) {
        log.info("Call service method: saveCurvePoint(CurvePoint curvePoint)");
        Date currentDate = new Date();
        curvePoint.setCreationDate(currentDate);
        curvePointRepository.save(curvePoint);
    }
}