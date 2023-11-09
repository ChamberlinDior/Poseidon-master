package com.openclassroomsprojet.poseidon.service;

import com.openclassroomsprojet.poseidon.domain.User;
import com.openclassroomsprojet.poseidon.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTests {

    @MockBean
    private UserRepository userRepositoryMock;
    @Autowired
    private IUserService userService;

    @Test
    public void givenUserList_whenFindAllUsers_thenReturnCorrectUserList() {
        User userTest = new User();
        userTest.setId(1);
        userTest.setUsername("User name test");
        userTest.setPassword("ABCdef123456@");
        userTest.setFullName("Full name test");
        userTest.setRole("USER");
        userTest.setEnabled(true);
        List<User> userListTest = Collections.singletonList(userTest);
        when(userRepositoryMock.findAll()).thenReturn(userListTest);
        List<User> userList = userService.findAllUsers();
        assertNotNull(userList);
        assertEquals(1, userList.size());
    }

    @Test
    public void givenUser_whenFindUserById_thenReturnCorrectUser() {
        Random randomInt = new Random();
        User userTest = new User();
        userTest.setId(1);
        userTest.setUsername("User name test");
        userTest.setPassword("ABCdef123456@");
        userTest.setFullName("Full name test");
        userTest.setRole("USER");
        userTest.setEnabled(true);
        when(userRepositoryMock.findById(anyInt())).thenReturn(Optional.of(userTest));
        Optional<User> optionalUser = userService.findUserById(randomInt.nextInt(100));
        User finalUser = new User();
        if (optionalUser.isPresent()) {
            finalUser = optionalUser.get();
        }
        assertNotNull(finalUser);
        assertEquals(1, finalUser.getId());
        assertEquals("User name test", finalUser.getUsername());
        assertEquals("ABCdef123456@", finalUser.getPassword());
        assertEquals("Full name test", finalUser.getFullName());
        assertEquals("USER", finalUser.getRole());
        assertTrue(finalUser.isEnabled());
    }

    @Test
    public void givenUser_whenDeleteUserById_thenVerify() {
        Random random = new Random();
        int randomInt = random.nextInt(100);
        doNothing().when(userRepositoryMock).deleteById(any());
        userService.deleteUserById(randomInt);
        verify(userRepositoryMock, times(1)).deleteById(randomInt);
    }

    @Test
    public void givenUser_whenSaveUser_thenVerify() {
        User userTest = new User();
        userTest.setId(1);
        userTest.setUsername("User name test");
        userTest.setPassword("ABCdef123456@");
        userTest.setFullName("Full name test");
        userTest.setRole("USER");
        userTest.setEnabled(true);
        when(userRepositoryMock.save(any())).thenReturn(userTest);
        userService.saveUser(userTest);
        verify(userRepositoryMock, times(1)).save(userTest);
    }
}
