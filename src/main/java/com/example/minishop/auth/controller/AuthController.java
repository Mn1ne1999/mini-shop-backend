package com.example.minishop.auth.controller;

import com.example.minishop.auth.dto.*;
import com.example.minishop.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public MeResponse me() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();
        String role = auth.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .orElse("UNKNOWN");

        return MeResponse.builder()
                .email(email)
                .role(role)
                .build();
    }

}
