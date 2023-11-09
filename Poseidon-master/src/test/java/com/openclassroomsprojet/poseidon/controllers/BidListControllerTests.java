package com.openclassroomsprojet.poseidon.controllers;

import com.openclassroomsprojet.poseidon.domain.BidList;
import com.openclassroomsprojet.poseidon.service.IBidListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTests {

    @MockBean
    private IBidListService bidListServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("spring")
    public void getAllBidListTest() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));
        verify(bidListServiceMock, times(1)).findAllBidList();
    }

    @Test
    public void getAllBidListTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getAddBidFormTest() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk()).andExpect(view().name("bidList/add"));
    }

    @Test
    public void getAddBidFormTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void postValidateTestWithValidObject() throws Exception {
        BidList bidListTest = new BidList();
        bidListTest.setAccount("Account test");
        bidListTest.setType("Test type");
        mockMvc.perform(post("/bidList/validate").flashAttr("bidList", bidListTest))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"));
        verify(bidListServiceMock, times(1)).saveBidList(bidListTest);
    }

    @Test
    @WithMockUser("spring")
    public void postValidateTestWithNotValidObject() throws Exception {
        BidList bidListTest = new BidList();
        mockMvc.perform(post("/bidList/validate").flashAttr("bidList", bidListTest))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
        verify(bidListServiceMock, times(0)).saveBidList(bidListTest);
    }

    @Test
    public void postValidateTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/bidList/validate"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getShowUpdateFormTestWithValidId() throws Exception {
        when(bidListServiceMock.findBidListById(1)).thenReturn(Optional.of(new BidList()));
        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
        verify(bidListServiceMock, times(1)).findBidListById(1);
    }

    @Test
    @WithMockUser("spring")
    public void getShowUpdateFormTestWithNotValidId() throws Exception {
        when(bidListServiceMock.findBidListById(99)).thenReturn(Optional.empty());
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/bidList/update/99"));
        });
        verify(bidListServiceMock, times(1)).findBidListById(99);
    }

    @Test
    public void getShowUpdateFormTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void postUpdateBidTestWithValidObject() throws Exception {
        BidList bidListTest = new BidList();
        bidListTest.setAccount("Account test");
        bidListTest.setType("Test type");
        doNothing().when(bidListServiceMock).saveBidList(any());
        mockMvc.perform(post("/bidList/update/1").flashAttr("bidList", bidListTest))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"));
        verify(bidListServiceMock, times(1)).saveBidList(any());
    }

    @Test
    @WithMockUser("spring")
    public void postUpdateBidTestWithNotValidObject() throws Exception {
        BidList bidListTest = new BidList();
        doNothing().when(bidListServiceMock).saveBidList(any());
        mockMvc.perform(post("/bidList/update/1").flashAttr("bidList", bidListTest))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
        verify(bidListServiceMock, times(0)).saveBidList(any());
    }

    @Test
    public void postUpdateBidTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/bidList/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getDeleteBidTestWithValidObject() throws Exception {
        when(bidListServiceMock.findBidListById(1)).thenReturn(Optional.of(new BidList()));
        doNothing().when(bidListServiceMock).deleteBidListById(1);
        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(view().name("redirect:/bidList/list"));
        verify(bidListServiceMock, times(1)).deleteBidListById(1);
        verify(bidListServiceMock, times(1)).findBidListById(1);
    }

    @Test
    @WithMockUser("spring")
    public void getDeleteBidTestWithNotValidObject() throws Exception {
        when(bidListServiceMock.findBidListById(1)).thenReturn(Optional.empty());
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/bidList/delete/1"));
        });
        verify(bidListServiceMock, times(0)).deleteBidListById(1);
        verify(bidListServiceMock, times(1)).findBidListById(1);
    }

    @Test
    public void getDeleteBidTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/bidList/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }
}