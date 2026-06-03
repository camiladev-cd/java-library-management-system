package com.librarymanagement.controller;

import com.librarymanagement.dto.AuthDTO;
import com.librarymanagement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register and login endpoints — no JWT required")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a USER account and returns a JWT token")
    public ResponseEntity<AuthDTO.Response> register(
            @Valid @RequestBody AuthDTO.Request request) {
        return ResponseEntity.status(201).body(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Validates credentials and returns a JWT token")
    public ResponseEntity<AuthDTO.Response> login(
            @Valid @RequestBody AuthDTO.Request request) {
        return ResponseEntity.ok(authService.login(request));
    }
}