package com.shopping.demo.serviceTest;

import com.shopping.demo.dao.User;
import com.shopping.demo.service.JwtUserDetailsService;
import com.shopping.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class JwtUserDetailsServiceTest {

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @Mock
    private UserService userService;

    private User validUser;
    private String validUsername;
    private String validPassword;
    private String invalidUsername;
    private String invalidPassword;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validUser = new User("1", "validUsername","user@example.com", "validPassword", "seller");
        validUsername = "validUsername";
        validPassword = "validPassword";
        invalidUsername = "invalidUsername";
        invalidPassword = "invalidPassword";
    }

    @Test
    void testLoadUserByUsernameWithValidCredentials() throws Exception {
        when(userService.findByName(validUsername)).thenReturn(validUser);

        User result = jwtUserDetailsService.loadUserByUsername(validUsername, validPassword);

        assertEquals(validUser, result);
    }

    @Test
    void testLoadUserByUsernameWithInvalidUsername() {
        when(userService.findByName(invalidUsername)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            jwtUserDetailsService.loadUserByUsername(invalidUsername, validPassword);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testLoadUserByUsernameWithInvalidPassword() {
        when(userService.findByName(validUsername)).thenReturn(validUser);

        Exception exception = assertThrows(Exception.class, () -> {
            jwtUserDetailsService.loadUserByUsername(validUsername, invalidPassword);
        });

        assertEquals("User not found", exception.getMessage());
    }
}
