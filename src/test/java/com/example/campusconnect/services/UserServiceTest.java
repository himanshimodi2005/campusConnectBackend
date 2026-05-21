package com.example.campusconnect.services;

import com.example.campusconnect.DTO.RegisterRequest;
import com.example.campusconnect.Repositories.UserRepository;
import com.example.campusconnect.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setPassword("password123");
        request.setEmail("john@example.com");
        request.setRole("USER");

        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        User savedUser = User.builder()
                .username("john")
                .password("encodedPassword")
                .email("john@example.com")
                .role("USER")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.registerUser(request);

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setPassword("password123");
        request.setEmail("john@example.com");
        request.setRole("USER");

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(request);
        });

        assertEquals("Username already taken", exception.getMessage());
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setPassword("password123");
        request.setEmail("john@example.com");
        request.setRole("USER");

        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(request);
        });

        assertEquals("Email already registered", exception.getMessage());
    }
}