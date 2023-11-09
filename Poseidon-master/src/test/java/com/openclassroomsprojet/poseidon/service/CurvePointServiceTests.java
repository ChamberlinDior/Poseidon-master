package com.openclassroomsprojet.poseidon.service;

import com.openclassroomsprojet.poseidon.domain.CurvePoint;
import com.openclassroomsprojet.poseidon.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CurvePointServiceTests {

    @MockBean
    private CurvePointRepository curvePointRepositoryMock;
    @Autowired
    private ICurvePointService curvePointService;

    @Test
    public void givenCurvePointList_whenFindAllCurvePoint_thenReturnCorrectCurvePointList() {
        CurvePoint curvePointTest = new CurvePoint();
        curvePointTest.setId(1);
        curvePointTest.setCurveId(2);
        curvePointTest.setAsOfDate(new Date());
        curvePointTest.setTerm(0.00);
        curvePointTest.setValue(0.00);
        List<CurvePoint> CurvePointListTest = Collections.singletonList(curvePointTest);
        when(curvePointRepositoryMock.findAll()).thenReturn(CurvePointListTest);
        List<CurvePoint> curvePointList = curvePointService.findAllCurvePoint();
        assertNotNull(curvePointList);
        assertEquals(1,curvePointList.size());
    }

    @Test
    public void givenCurvePoint_whenFindCurvePointById_thenReturnCorrectCurvePoint() {
        CurvePoint curvePointTest = new CurvePoint();
        Random randomInt = new Random();
        curvePointTest.setId(randomInt.nextInt(100));
        curvePointTest.setCurveId(2);
        curvePointTest.setAsOfDate(new Date());
        curvePointTest.setTerm(0.00);
        curvePointTest.setValue(0.00);
        when(curvePointRepositoryMock.findById(any())).thenReturn(Optional.of(curvePointTest));
        Optional<CurvePoint> curvePoint = curvePointService.findCurvePointById(randomInt.nextInt(100));
        assertNotNull(curvePoint);
        assertEquals(2, curvePointTest.getCurveId());
        assertEquals(0.00, curvePointTest.getTerm());
        assertEquals(0.00, curvePointTest.getValue());
    }

    @Test
    public void givenCurvePoint_whenDeleteCurvePointById_thenVerify() {
        Random random = new Random();
        int randomInt = random.nextInt(100);
        doNothing().when(curvePointRepositoryMock).deleteById(any());
        curvePointService.deleteCurvePointById(randomInt);
        verify(curvePointRepositoryMock, times(1)).deleteById(randomInt);
    }

    @Test
    public void givenCurvePoint_whenSaveCurvePoint_thenVerify() {
        CurvePoint curvePointTest = new CurvePoint();
        curvePointTest.setId(1);
        curvePointTest.setCurveId(2);
        curvePointTest.setAsOfDate(new Date());
        curvePointTest.setTerm(0.00);
        curvePointTest.setValue(0.00);
        when(curvePointRepositoryMock.save(any())).thenReturn(curvePointTest);
        curvePointService.saveCurvePoint(curvePointTest);
        verify(curvePointRepositoryMock, times(1)).save(curvePointTest);
    }
}