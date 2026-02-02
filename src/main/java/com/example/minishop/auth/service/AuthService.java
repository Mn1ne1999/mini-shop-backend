package com.example.minishop.auth.service;

import com.example.minishop.auth.dto.*;
import com.example.minishop.auth.security.JwtService;
import com.example.minishop.common.exception.ValidationException;
import com.example.minishop.user.model.Role;
import com.example.minishop.user.model.User;
import com.example.minishop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.generateToken(user.getEmail(), user.getRole().name()))
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ValidationException("Invalid credentials"));


        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ValidationException("Invalid credentials");
        }

        return AuthResponse.builder()
                .token(jwtService.generateToken(user.getEmail(), user.getRole().name()))
                .build();
    }
}
