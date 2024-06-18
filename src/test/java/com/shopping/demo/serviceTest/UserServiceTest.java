package com.shopping.demo.serviceTest;

import com.shopping.demo.dao.User;
import com.shopping.demo.model.SignUpRequestDTO;
import com.shopping.demo.service.UserService;
import com.shopping.demo.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private List<User> userList;
    private SignUpRequestDTO signUpRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("1", "username", "password", "buyer", "user@example.com");
        userList = new ArrayList<>();
        userList.add(user);
        signUpRequest = new SignUpRequestDTO("username", "password", "buyer", "user@example.com");
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertEquals(userList, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User result = userService.getUserById("1");

        assertEquals(user, result);
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.saveUser(signUpRequest);

        assertEquals(user, result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUserById() {
        userService.deleteUserById("1");

        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    void testFindByName() {
        when(userRepository.findByUsername("username")).thenReturn(user);

        User result = userService.findByName("username");

        assertEquals(user, result);
        verify(userRepository, times(1)).findByUsername("username");
    }
}
