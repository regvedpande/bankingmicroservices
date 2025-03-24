package com.example.banking_system.service;

import com.example.banking_system.model.User;
import com.example.banking_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Enables Mockito support
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        // Any setup can go here if needed
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User registeredUser = userService.registerUser(user);

        // Assert
        assertEquals("testuser", registeredUser.getUsername());
        assertEquals(0.0, registeredUser.getBalance());
    }

    @Test
    public void testFindByUsername() {
        // Arrange
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        user.setEmail("test@example.com");

        when(userRepository.findByUsername(username)).thenReturn(user);

        // Act
        User foundUser = userService.findByUsername(username);

        // Assert
        assertEquals(username, foundUser.getUsername());
        assertEquals("test@example.com", foundUser.getEmail());
    }
}