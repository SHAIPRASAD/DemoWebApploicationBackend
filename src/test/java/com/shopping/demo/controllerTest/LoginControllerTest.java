package com.shopping.demo.controllerTest;

import com.shopping.demo.controller.LoginController;
import com.shopping.demo.dao.User;
import com.shopping.demo.model.LoginRequest;
import com.shopping.demo.model.ResponseDTO;
import com.shopping.demo.service.JwtUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private JwtUserDetailsService userDetailsService;

    private LoginRequest validLoginRequest;
    private LoginRequest invalidLoginRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validLoginRequest = new LoginRequest("validUsername", "validPassword");
        invalidLoginRequest = new LoginRequest("invalidUsername", "invalidPassword");
    }

    @Test
    public void testCreateAuthenticationToken_ValidCredentials() throws Exception {
        User validUser = new User("1","validUsername","email@gmail.com","validPassword","seller");
        when(userDetailsService.loadUserByUsername("validUsername", "validPassword")).thenReturn(validUser);

        ResponseDTO response = loginController.createAuthenticationToken(validLoginRequest);

        assertEquals(200, response.getCode());
        assertEquals("User logged in", response.getMessage());
        assertEquals("Success", response.getStatus());
    }

    @Test
    public void testCreateAuthenticationToken_InvalidCredentials() throws Exception {
        when(userDetailsService.loadUserByUsername("invalidUsername", "invalidPassword")).thenThrow(new Exception("Invalid credentials"));

        ResponseDTO response = loginController.createAuthenticationToken(invalidLoginRequest);

        assertEquals(400, response.getCode());
        assertEquals("Invalid credantials", response.getMessage());
        assertEquals("Failure", response.getStatus());
    }
}
