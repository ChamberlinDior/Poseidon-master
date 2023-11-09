package com.openclassroomsprojet.poseidon.controllers;

import com.openclassroomsprojet.poseidon.domain.Rating;
import com.openclassroomsprojet.poseidon.service.IRatingService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTests {

    @MockBean
    private IRatingService ratingServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("spring")
    public void getAllRatingTest() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
        verify(ratingServiceMock, times(1)).findAllRating();
    }

    @Test
    public void getAllRatingTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getAddRatingFormTest() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk()).andExpect(view().name("rating/add"));
    }

    @Test
    public void getAddRatingFormTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void postValidateTestWithValidObject() throws Exception {
        Rating ratingTest = new Rating();
        ratingTest.setMoodysRating("test");
        ratingTest.setSandPrating("test");
        ratingTest.setFitchRating("test");
        ratingTest.setOrderNumber(1);
        mockMvc.perform(post("/rating/validate").flashAttr("rating", ratingTest))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"));
        verify(ratingServiceMock, times(1)).saveRating(ratingTest);
    }

    @Test
    @WithMockUser("spring")
    public void postValidateTestWithNotValidObject() throws Exception {
        Rating ratingTest = new Rating();
        mockMvc.perform(post("/rating/validate").flashAttr("rating", ratingTest))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
        verify(ratingServiceMock, times(0)).saveRating(ratingTest);
    }

    @Test
    public void postValidateTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/rating/validate"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getShowUpdateFormTestWithValidId() throws Exception {
        when(ratingServiceMock.findRatingById(1)).thenReturn(Optional.of(new Rating()));
        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
        verify(ratingServiceMock, times(1)).findRatingById(1);
    }

    @Test
    @WithMockUser("spring")
    public void getShowUpdateFormTestWithNotValidId() throws Exception {
        when(ratingServiceMock.findRatingById(99)).thenReturn(Optional.empty());
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/rating/update/99"));
        });
        verify(ratingServiceMock, times(1)).findRatingById(99);
    }

    @Test
    public void getShowUpdateFormTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void postUpdateRatingTestWithValidObject() throws Exception {
        Rating ratingTest = new Rating();
        ratingTest.setMoodysRating("test");
        ratingTest.setSandPrating("test");
        ratingTest.setFitchRating("test");
        ratingTest.setOrderNumber(1);
        doNothing().when(ratingServiceMock).saveRating(any());
        mockMvc.perform(post("/rating/update/1").flashAttr("rating", ratingTest))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"));
        verify(ratingServiceMock, times(1)).saveRating(any());
    }

    @Test
    @WithMockUser("spring")
    public void postUpdateRatingTestWithNotValidObject() throws Exception {
        Rating ratingTest = new Rating();
        doNothing().when(ratingServiceMock).saveRating(any());
        mockMvc.perform(post("/rating/update/1").flashAttr("rating", ratingTest))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
        verify(ratingServiceMock, times(0)).saveRating(any());
    }

    @Test
    public void postUpdateRatingTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/rating/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getDeleteRatingTestWithValidObject() throws Exception {
        when(ratingServiceMock.findRatingById(1)).thenReturn(Optional.of(new Rating()));
        doNothing().when(ratingServiceMock).deleteRatingById(1);
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(view().name("redirect:/rating/list"));
        verify(ratingServiceMock, times(1)).deleteRatingById(1);
        verify(ratingServiceMock, times(1)).findRatingById(1);
    }

    @Test
    @WithMockUser("spring")
    public void getDeleteRatingTestWithNotValidObject() throws Exception {
        when(ratingServiceMock.findRatingById(1)).thenReturn(Optional.empty());
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/rating/delete/1"));
        });
        verify(ratingServiceMock, times(0)).deleteRatingById(1);
        verify(ratingServiceMock, times(1)).findRatingById(1);
    }

    @Test
    public void getDeleteRatingTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/rating/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }
}