package com.openclassroomsprojet.poseidon.controllers;

import com.openclassroomsprojet.poseidon.domain.User;
import com.openclassroomsprojet.poseidon.service.IUserService;
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
public class UserControllerTests {

    @MockBean
    private IUserService userServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllUserTest() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
        verify(userServiceMock, times(1)).findAllUsers();
    }

    @Test
    public void getAllUserTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAddUserFormTest() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk()).andExpect(view().name("user/add"));
    }

    @Test
    public void getAddUserFormTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postValidateTestWithValidObject() throws Exception {
        User userTest = new User();
        userTest.setUsername("test");
        userTest.setPassword("ABCabc123@");
        userTest.setFullName("test");
        userTest.setRole("USER");
        mockMvc.perform(post("/user/validate").flashAttr("user", userTest))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));
        verify(userServiceMock, times(1)).saveUser(userTest);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postValidateTestWithNotValidObject() throws Exception {
        User userTest = new User();
        userTest.setUsername("test");
        userTest.setPassword("azerty");
        userTest.setFullName("test");
        userTest.setRole("USER");
        mockMvc.perform(post("/user/validate").flashAttr("user", userTest))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
        verify(userServiceMock, times(0)).saveUser(userTest);
    }

    @Test
    public void postValidateTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/user/validate"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getShowUpdateFormTestWithValidId() throws Exception {
        when(userServiceMock.findUserById(1)).thenReturn(Optional.of(new User()));
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
        verify(userServiceMock, times(1)).findUserById(1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getShowUpdateFormTestWithNotValidId() throws Exception {
        when(userServiceMock.findUserById(99)).thenReturn(Optional.empty());
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/user/update/99"));
        });
        verify(userServiceMock, times(1)).findUserById(99);
    }

    @Test
    public void getShowUpdateFormTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postUpdateUserTestWithValidObject() throws Exception {
        User userTest = new User();
        userTest.setUsername("test");
        userTest.setPassword("ABCabc123@");
        userTest.setFullName("test");
        userTest.setRole("USER");
        doNothing().when(userServiceMock).saveUser(any());
        mockMvc.perform(post("/user/update/1").flashAttr("user", userTest))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));
        verify(userServiceMock, times(1)).saveUser(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postUpdateUserTestWithNotValidObject() throws Exception {
        User userTest = new User();
        userTest.setUsername("test");
        userTest.setPassword("azerty");
        userTest.setFullName("test");
        userTest.setRole("USER");
        mockMvc.perform(post("/user/update/1").flashAttr("user", userTest))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
        verify(userServiceMock, times(0)).saveUser(any());
    }

    @Test
    public void postUpdateUserTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/user/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getDeleteUserTestWithValidObject() throws Exception {
        when(userServiceMock.findUserById(1)).thenReturn(Optional.of(new User()));
        doNothing().when(userServiceMock).deleteUserById(1);
        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(view().name("redirect:/user/list"));
        verify(userServiceMock, times(1)).deleteUserById(1);
        verify(userServiceMock, times(1)).findUserById(1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getDeleteUserTestWithNotValidObject() throws Exception {
        when(userServiceMock.findUserById(1)).thenReturn(Optional.empty());
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/user/delete/1"));
        });
        verify(userServiceMock, times(0)).deleteUserById(1);
        verify(userServiceMock, times(1)).findUserById(1);
    }

    @Test
    public void getDeleteUserTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/user/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }
}