package com.example.campusconnect.controllers;


import com.example.campusconnect.DTO.JwtResponse;
import com.example.campusconnect.DTO.LoginRequest;
import com.example.campusconnect.DTO.RegisterRequest;
import com.example.campusconnect.SecurityConfig.JwtUtil;
import com.example.campusconnect.entities.User;
import com.example.campusconnect.services.UserService;
import jakarta.validation.Valid; // New Import
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    // Added @Valid
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.registerUser(request);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            // NOTE: Consider adding a GlobalExceptionHandler for better error handling
            return new ResponseEntity<>("Error registering user: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    // Added @Valid
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = userService.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            }

            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

            return ResponseEntity.ok(
                    JwtResponse.builder()
                            .token(token)
                            .role(user.getRole())
                            .build()
            );
        } catch (Exception e) {
            return new ResponseEntity<>("Login failed: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}