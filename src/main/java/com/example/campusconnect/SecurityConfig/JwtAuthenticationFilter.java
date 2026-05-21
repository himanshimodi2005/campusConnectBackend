//package com.example.campusconnect.SecurityConfig;
//
//import com.example.campusconnect.Repositories.UserRepository;
//import com.example.campusconnect.services.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//    private final UserRepository userRepository;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        final String header = request.getHeader("Authorization");
//        final String token;
//        final String username;
//
//        if (header == null || !header.startsWith("Bearer ")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        token = header.substring(7);
//        username = jwtUtil.extractUsername(token);
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            var userOptional = userRepository.findByUsername(username);
//            if (userOptional.isPresent() && jwtUtil.validateToken(token)) {
//                var user = userOptional.get();
//                var authToken = new UsernamePasswordAuthenticationToken(
//                        user.getUsername(), null, java.util.List.of(() -> user.getRole()));
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        chain.doFilter(request, response);
//    }
//}


package com.example.campusconnect.SecurityConfig;

import com.example.campusconnect.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        final String token;
        final String username;

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        token = header.substring(7);
        username = jwtUtil.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent() && jwtUtil.validateToken(token)) {
                var user = userOptional.get();

                // FIX: Create proper SimpleGrantedAuthority instead of lambda
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

                var authToken = new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        null,
                        Collections.singletonList(authority)  // Changed this line
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                // Debug log (remove after testing)
                System.out.println("User authenticated: " + username + " with role: " + user.getRole());
            }
        }
        chain.doFilter(request, response);
    }
}