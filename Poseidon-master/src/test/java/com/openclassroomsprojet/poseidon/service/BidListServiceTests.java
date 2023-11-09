package com.openclassroomsprojet.poseidon.service;

import com.openclassroomsprojet.poseidon.domain.BidList;
import com.openclassroomsprojet.poseidon.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BidListServiceTests {

    @MockBean
    private BidListRepository bidListRepositoryMock;
    @Autowired
    private IBidListService bidListService;

    @Test
    public void givenBidListList_whenFindAllBidList_thenReturnCorrectBidListList() {
        BidList bidListTest = new BidList();
        bidListTest.setBidListId(1);
        bidListTest.setAccount("Account test");
        bidListTest.setType("Test type");
        List<BidList> bidListListTest = Collections.singletonList(bidListTest);
        when(bidListRepositoryMock.findAll()).thenReturn(bidListListTest);
        List<BidList> bidListList = bidListService.findAllBidList();
        assertNotNull(bidListList);
        assertEquals(1,bidListList.size());
    }

    @Test
    public void givenBidList_whenFindBidListById_thenReturnCorrectBidList() {
        BidList bidListTest = new BidList();
        Random randomInt = new Random();
        bidListTest.setBidListId(randomInt.nextInt(100));
        bidListTest.setAccount("Account test");
        bidListTest.setType("Test type");
        bidListTest.setBidQuantity(2.0);
        when(bidListRepositoryMock.findBidListByBidListId(anyInt())).thenReturn(Optional.of(bidListTest));
        Optional<BidList> optionalBidList = bidListService.findBidListById(randomInt.nextInt(100));
        BidList finalBidList = new BidList();
        if (optionalBidList.isPresent()) {
            finalBidList = optionalBidList.get();
        }
        assertNotNull(finalBidList);
        assertEquals("Account test", finalBidList.getAccount());
        assertEquals("Test type", finalBidList.getType());
    }

    @Test
    public void givenBidList_whenDeleteBidListById_thenVerify() {
        Random random = new Random();
        int randomInt = random.nextInt(100);
        doNothing().when(bidListRepositoryMock).deleteById(any());
        bidListService.deleteBidListById(randomInt);
        verify(bidListRepositoryMock, times(1)).deleteById(randomInt);
    }

    @Test
    public void givenBidList_whenSaveBidList_thenVerify() {
        BidList bidListTest = new BidList();
        bidListTest.setBidListId(1);
        bidListTest.setAccount("Account test");
        bidListTest.setType("Test type");
        when(bidListRepositoryMock.save(any())).thenReturn(bidListTest);
        bidListService.saveBidList(bidListTest);
        verify(bidListRepositoryMock, times(1)).save(bidListTest);
    }
}