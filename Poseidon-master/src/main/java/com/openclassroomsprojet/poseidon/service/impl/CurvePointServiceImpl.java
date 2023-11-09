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
 * This class contains methods that allow performing CRUD actions by calling the curvePoint repository
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Slf4j
@Service
public class CurvePointServiceImpl implements ICurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Allows to search all the CurvePoints contained in the database
     *
     * @return A list of CurvePoint
     */
    @Override
    public List<CurvePoint> findAllCurvePoint() {
        log.info("Call service method: findAllCurvePoint()");
        return curvePointRepository.findAll();
    }

    /**
     * Searches for a CurvePoint object from its identifier
     *
     * @param id The identifier of the object to find
     * @return An optional which may or may not contain the requested object
     */
    @Override
    public Optional<CurvePoint> findCurvePointById(int id) {
        log.info("Call service method: findCurvePointById(int id)");
        return curvePointRepository.findById(id);
    }

    /**
     * Delete a CurvePoint
     *
     * @param id The identifier of the object to delete
     */
    @Override
    public void deleteCurvePointById(int id) {
        log.info("Call service method: deleteCurvePointById(int id)");
        curvePointRepository.deleteById(id);
    }

    /**
     * Save a CurvePoint
     *
     * @param curvePoint Object to be saved
     */
    @Override
    public void saveCurvePoint(CurvePoint curvePoint) {
        log.info("Call service method: saveCurvePoint(CurvePoint curvePoint)");
        Date currentDate = new Date();
        curvePoint.setCreationDate(currentDate);
        curvePointRepository.save(curvePoint);
    }
}