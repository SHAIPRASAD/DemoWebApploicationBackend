package com.shopping.demo.controllerTest;

import com.shopping.demo.controller.UserController;
import com.shopping.demo.dao.User;
import com.shopping.demo.model.ResponseDTO;
import com.shopping.demo.model.SignUpRequestDTO;
import com.shopping.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private SignUpRequestDTO validSignUpRequest;
    private SignUpRequestDTO invalidSignUpRequest;
    private User persistedUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validSignUpRequest = new SignUpRequestDTO("validUsername", "validPassword", "email@gmail.com","buyer");
        invalidSignUpRequest = new SignUpRequestDTO("", "", "","");
        persistedUser = new User("1","validUsername",  "email@gmail.com","validPassword", "buyer");
    }

    @Test
    void testAddUser_ValidRequest() throws Exception {
        when(userService.saveUser(validSignUpRequest)).thenReturn(persistedUser);

        ResponseDTO response = userController.add(validSignUpRequest);

        assertEquals(200, response.getCode());
        assertEquals("User added", response.getMessage());
        assertEquals("Success", response.getStatus());
        verify(userService, times(1)).saveUser(validSignUpRequest);
    }

    @Test
    void testAddUser_InvalidRequest() throws Exception {
        ResponseDTO response = userController.add(invalidSignUpRequest);

        assertEquals(400, response.getCode());
        assertEquals("Check the request", response.getMessage());
        assertEquals("Failure", response.getStatus());
        verify(userService, never()).saveUser(invalidSignUpRequest);
    }
}
