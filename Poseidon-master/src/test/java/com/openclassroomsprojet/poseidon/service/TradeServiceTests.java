package com.openclassroomsprojet.poseidon.service;

import com.openclassroomsprojet.poseidon.domain.Trade;
import com.openclassroomsprojet.poseidon.repositories.TradeRepository;
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
public class TradeServiceTests {

    @MockBean
    private TradeRepository tradeRepositoryMock;
    @Autowired
    private ITradeService tradeService;

    @Test
    public void givenRuleNameList_whenFindAllRuleName_thenReturnCorrectRuleNameList() {
        Trade tradeTest = new Trade();
        tradeTest.setTradeId(1);
        tradeTest.setAccount("Account test");
        tradeTest.setType("Type test");
        List<Trade> tradeListTest = Collections.singletonList(tradeTest);
        when(tradeRepositoryMock.findAll()).thenReturn(tradeListTest);
        List<Trade> ratingList = tradeService.findAllTrade();
        assertNotNull(ratingList);
        assertEquals(1, ratingList.size());
    }

    @Test
    public void givenRuleName_whenFindRuleNameById_thenReturnCorrectRuleName() {
        Random randomInt = new Random();
        Trade tradeTest = new Trade();
        tradeTest.setTradeId(1);
        tradeTest.setAccount("Account test");
        tradeTest.setType("Type test");
        when(tradeRepositoryMock.findById(any())).thenReturn(Optional.of(tradeTest));
        Optional<Trade> optionalTrade = tradeService.findTradeById(randomInt.nextInt(100));
        Trade finalTrade = new Trade();
        if (optionalTrade.isPresent()) {
            finalTrade = optionalTrade.get();
        }
        assertNotNull(finalTrade);
        assertEquals(1, finalTrade.getTradeId());
        assertEquals("Account test", finalTrade.getAccount());
        assertEquals("Type test", finalTrade.getType());
    }

    @Test
    public void givenRuleName_whenDeleteRuleNameById_thenVerify() {
        Random random = new Random();
        int randomInt = random.nextInt(100);
        doNothing().when(tradeRepositoryMock).deleteById(any());
        tradeService.deleteTradeById(randomInt);
        verify(tradeRepositoryMock, times(1)).deleteById(randomInt);
    }

    @Test
    public void givenRuleName_whenSaveRuleName_thenVerify() {
        Trade tradeTest = new Trade();
        tradeTest.setTradeId(1);
        tradeTest.setAccount("Account test");
        tradeTest.setType("Type test");
        when(tradeRepositoryMock.save(any())).thenReturn(tradeTest);
        tradeService.saveTrade(tradeTest);
        verify(tradeRepositoryMock, times(1)).save(tradeTest);
    }
}