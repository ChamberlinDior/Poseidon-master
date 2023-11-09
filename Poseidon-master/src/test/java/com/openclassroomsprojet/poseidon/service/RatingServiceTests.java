package com.openclassroomsprojet.poseidon.service;

import com.openclassroomsprojet.poseidon.domain.Rating;
import com.openclassroomsprojet.poseidon.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RatingServiceTests {

    @MockBean
    private RatingRepository ratingRepositoryMock;
    @Autowired
    private IRatingService ratingService;

    @Test
    public void givenRatingList_whenFindAllRating_thenReturnCorrectRatingList() {
        Rating ratingTest = new Rating();
        ratingTest.setId(1);
        ratingTest.setMoodysRating("Moody's rating test");
        ratingTest.setSandPrating("Sand Prating test");
        ratingTest.setFitchRating("Fitch rating test");
        ratingTest.setOrderNumber(1);
        List<Rating> ratingListTest = Collections.singletonList(ratingTest);
        when(ratingRepositoryMock.findAll()).thenReturn(ratingListTest);
        List<Rating> ratingList = ratingService.findAllRating();
        assertNotNull(ratingList);
        assertEquals(1,ratingList.size());
    }

    @Test
    public void givenRating_whenFindRatingById_thenReturnCorrectRating() {
        Rating ratingTest = new Rating();
        Random randomInt = new Random();
        ratingTest.setId(randomInt.nextInt(100));
        ratingTest.setMoodysRating("Moody's rating test");
        ratingTest.setSandPrating("Sand Prating test");
        ratingTest.setFitchRating("Fitch rating test");
        ratingTest.setOrderNumber(2);
        when(ratingRepositoryMock.findById(any())).thenReturn(Optional.of(ratingTest));
        Optional<Rating> rating = ratingService.findRatingById(randomInt.nextInt(100));
        assertNotNull(rating);
        assertEquals("Moody's rating test", ratingTest.getMoodysRating());
        assertEquals("Sand Prating test", ratingTest.getSandPrating());
        assertEquals("Fitch rating test", ratingTest.getFitchRating());
        assertEquals(2, ratingTest.getOrderNumber());
    }

    @Test
    public void givenRating_whenDeleteRatingById_thenVerify() {
        Random random = new Random();
        int randomInt = random.nextInt(100);
        doNothing().when(ratingRepositoryMock).deleteById(any());
        ratingService.deleteRatingById(randomInt);
        verify(ratingRepositoryMock, times(1)).deleteById(randomInt);
    }

    @Test
    public void givenRating_whenSaveRating_thenVerify() {
        Rating ratingTest = new Rating();
        Random randomInt = new Random();
        ratingTest.setId(randomInt.nextInt(100));
        ratingTest.setMoodysRating("Moody's rating test");
        ratingTest.setSandPrating("Sand Prating test");
        ratingTest.setFitchRating("Fitch rating test");
        ratingTest.setOrderNumber(2);
        when(ratingRepositoryMock.save(any())).thenReturn(ratingTest);
        ratingService.saveRating(ratingTest);
        verify(ratingRepositoryMock, times(1)).save(ratingTest);
    }
}