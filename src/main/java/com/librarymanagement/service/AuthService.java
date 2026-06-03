package com.librarymanagement.service;

import com.librarymanagement.dto.AuthDTO;
import com.librarymanagement.exception.DuplicateResourceException;
import com.librarymanagement.model.User;
import com.librarymanagement.enums.Role;
import com.librarymanagement.repository.UserRepository;
import com.librarymanagement.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository    userRepository;
    private final PasswordEncoder   passwordEncoder;
    private final JwtService        jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthDTO.Response register(AuthDTO.Request request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(
                    "Email already registered: " + request.email());
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)   // default role — promote to ADMIN manually in DB
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthDTO.Response(token);
    }

    public AuthDTO.Response login(AuthDTO.Request request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow();

        String token = jwtService.generateToken(user);
        return new AuthDTO.Response(token);
    }
}