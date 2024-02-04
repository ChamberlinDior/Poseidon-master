package com.openclassroomsprojet.poseidon.service;

import com.openclassroomsprojet.poseidon.domain.CurvePoint;
import java.util.List;
import java.util.Optional;

/**
 * @author chamberlin dior
 * @version 1.0
 */
public interface ICurvePointService {

    List<CurvePoint> findAllCurvePoint();

    Optional<CurvePoint> findCurvePointById(int id);

    void deleteCurvePointById(int id);

    void saveCurvePoint(CurvePoint curvePoint);
}