package com.openclassroomsprojet.poseidon.controllers;

import com.openclassroomsprojet.poseidon.domain.Trade;
import com.openclassroomsprojet.poseidon.service.ITradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTests {

    @MockBean
    private ITradeService tradeServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("spring")
    public void getAllTradeTest() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
        verify(tradeServiceMock, times(1)).findAllTrade();
    }

    @Test
    public void getAllTradeTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getAddTradeFormTest() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk()).andExpect(view().name("trade/add"));
    }

    @Test
    public void getAddTradeFormTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void postValidateTestWithValidObject() throws Exception {
        Trade tradeTest = new Trade();
        tradeTest.setAccount("test");
        tradeTest.setType("test");
        mockMvc.perform(post("/trade/validate").flashAttr("trade", tradeTest))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/trade/list"));
        verify(tradeServiceMock, times(1)).saveTrade(tradeTest);
    }

    @Test
    @WithMockUser("spring")
    public void postValidateTestWithNotValidObject() throws Exception {
        Trade tradeTest = new Trade();
        mockMvc.perform(post("/trade/validate").flashAttr("trade", tradeTest))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
        verify(tradeServiceMock, times(0)).saveTrade(tradeTest);
    }

    @Test
    public void postValidateTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/trade/validate"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getShowUpdateFormTestWithValidId() throws Exception {
        when(tradeServiceMock.findTradeById(1)).thenReturn(Optional.of(new Trade()));
        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
        verify(tradeServiceMock, times(1)).findTradeById(1);
    }

    @Test
    @WithMockUser("spring")
    public void getShowUpdateFormTestWithNotValidId() throws Exception {
        when(tradeServiceMock.findTradeById(99)).thenReturn(Optional.empty());
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/trade/update/99"));
        });
        verify(tradeServiceMock, times(1)).findTradeById(99);
    }

    @Test
    public void getShowUpdateFormTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void postUpdateTradeTestWithValidObject() throws Exception {
        Trade tradeTest = new Trade();
        tradeTest.setAccount("test");
        tradeTest.setType("test");
        doNothing().when(tradeServiceMock).saveTrade(any());
        mockMvc.perform(post("/trade/update/1").flashAttr("trade", tradeTest))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/trade/list"));
        verify(tradeServiceMock, times(1)).saveTrade(any());
    }

    @Test
    @WithMockUser("spring")
    public void postUpdateTradeTestWithNotValidObject() throws Exception {
        Trade tradeTest = new Trade();
        mockMvc.perform(post("/trade/update/1").flashAttr("trade", tradeTest))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
        verify(tradeServiceMock, times(0)).saveTrade(any());
    }

    @Test
    public void postUpdateTradeTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/trade/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getDeleteTradeTestWithValidObject() throws Exception {
        when(tradeServiceMock.findTradeById(1)).thenReturn(Optional.of(new Trade()));
        doNothing().when(tradeServiceMock).deleteTradeById(1);
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(view().name("redirect:/trade/list"));
        verify(tradeServiceMock, times(1)).deleteTradeById(1);
        verify(tradeServiceMock, times(1)).findTradeById(1);
    }

    @Test
    @WithMockUser("spring")
    public void getDeleteTradeTestWithNotValidObject() throws Exception {
        when(tradeServiceMock.findTradeById(1)).thenReturn(Optional.empty());
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/trade/delete/1"));
        });
        verify(tradeServiceMock, times(0)).deleteTradeById(1);
        verify(tradeServiceMock, times(1)).findTradeById(1);
    }

    @Test
    public void getDeleteTradeTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/trade/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }
}