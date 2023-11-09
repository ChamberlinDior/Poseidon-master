package com.openclassroomsprojet.poseidon.controllers;

import com.openclassroomsprojet.poseidon.domain.CurvePoint;
import com.openclassroomsprojet.poseidon.service.ICurvePointService;
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
public class CurvePointControllerTests {

    @MockBean
    private ICurvePointService curvePointServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("spring")
    public void getAllCurvePointTest() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"));
        verify(curvePointServiceMock, times(1)).findAllCurvePoint();
    }

    @Test
    public void getAllCurvePointTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getAddCurvePointFormTest() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk()).andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void getAddCurvePointFormTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void postValidateTestWithValidObject() throws Exception {
        CurvePoint curvePointTest = new CurvePoint();
        curvePointTest.setCurveId(1);
        curvePointTest.setTerm(0.0);
        curvePointTest.setValue(0.0);
        mockMvc.perform(post("/curvePoint/validate").flashAttr("curvePoint", curvePointTest))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvePoint/list"));
        verify(curvePointServiceMock, times(1)).saveCurvePoint(curvePointTest);
    }

    @Test
    @WithMockUser("spring")
    public void postValidateTestWithNotValidObject() throws Exception {
        CurvePoint curvePointTest = new CurvePoint();
        mockMvc.perform(post("/curvePoint/validate").flashAttr("curvePoint", curvePointTest))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
        verify(curvePointServiceMock, times(0)).saveCurvePoint(curvePointTest);
    }

    @Test
    public void postValidateTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/curvePoint/validate"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getShowUpdateFormTestWithValidId() throws Exception {
        when(curvePointServiceMock.findCurvePointById(1)).thenReturn(Optional.of(new CurvePoint()));
        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
        verify(curvePointServiceMock, times(1)).findCurvePointById(1);
    }

    @Test
    @WithMockUser("spring")
    public void getShowUpdateFormTestWithNotValidId() throws Exception {
        when(curvePointServiceMock.findCurvePointById(99)).thenReturn(Optional.empty());
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/curvePoint/update/99"));
        });
        verify(curvePointServiceMock, times(1)).findCurvePointById(99);
    }

    @Test
    public void getShowUpdateFormTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void postUpdateCurvePointTestWithValidObject() throws Exception {
        CurvePoint curvePointTest = new CurvePoint();
        curvePointTest.setCurveId(1);
        curvePointTest.setTerm(0.0);
        curvePointTest.setValue(0.0);
        doNothing().when(curvePointServiceMock).saveCurvePoint(any());
        mockMvc.perform(post("/curvePoint/update/1").flashAttr("curvePoint", curvePointTest))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvePoint/list"));
        verify(curvePointServiceMock, times(1)).saveCurvePoint(any());
    }

    @Test
    @WithMockUser("spring")
    public void postUpdateCurvePointTestWithNotValidObject() throws Exception {
        CurvePoint curvePointTest = new CurvePoint();
        doNothing().when(curvePointServiceMock).saveCurvePoint(any());
        mockMvc.perform(post("/curvePoint/update/1").flashAttr("curvePoint", curvePointTest))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
        verify(curvePointServiceMock, times(0)).saveCurvePoint(any());
    }

    @Test
    public void postUpdateCurvePointTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getDeleteCurvePointTestWithValidObject() throws Exception {
        when(curvePointServiceMock.findCurvePointById(1)).thenReturn(Optional.of(new CurvePoint()));
        doNothing().when(curvePointServiceMock).deleteCurvePointById(1);
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(view().name("redirect:/curvePoint/list"));
        verify(curvePointServiceMock, times(1)).deleteCurvePointById(1);
        verify(curvePointServiceMock, times(1)).findCurvePointById(1);
    }

    @Test
    @WithMockUser("spring")
    public void getDeleteCurvePointTestWithNotValidObject() throws Exception {
        when(curvePointServiceMock.findCurvePointById(1)).thenReturn(Optional.empty());
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/curvePoint/delete/1"));
        });
        verify(curvePointServiceMock, times(0)).deleteCurvePointById(1);
        verify(curvePointServiceMock, times(1)).findCurvePointById(1);
    }

    @Test
    public void getDeleteCurvePointTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/curvePoint/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }
}