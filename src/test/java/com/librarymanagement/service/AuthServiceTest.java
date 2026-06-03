package com.librarymanagement.service;

import com.librarymanagement.dto.AuthDTO;
import com.librarymanagement.enums.Role;
import com.librarymanagement.exception.DuplicateResourceException;
import com.librarymanagement.model.User;
import com.librarymanagement.repository.UserRepository;
import com.librarymanagement.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Tests")
class AuthServiceTest {

    @Mock private UserRepository        userRepository;
    @Mock private PasswordEncoder       passwordEncoder;
    @Mock private JwtService            jwtService;
    @Mock private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private AuthDTO.Request request;
    private User user;

    @BeforeEach
    void setUp() {
        request = new AuthDTO.Request("test@example.com", "password123");

        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encoded_password")
                .role(Role.USER)
                .build();
    }

    // ── register ──────────────────────────────────────────────────

    @Test
    @DisplayName("Should register user and return JWT when email is new")
    void shouldRegisterUser_whenEmailIsNew() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt_token");

        AuthDTO.Response result = authService.register(request);

        assertThat(result.token()).isEqualTo("jwt_token");
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("password123");
    }

    @Test
    @DisplayName("Should throw DuplicateResourceException when email already exists")
    void shouldThrowException_whenEmailAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("test@example.com");

        verify(userRepository, never()).save(any());
    }

    // ── login ─────────────────────────────────────────────────────

    @Test
    @DisplayName("Should login and return JWT when credentials are valid")
    void shouldLogin_whenCredentialsAreValid() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt_token");

        AuthDTO.Response result = authService.login(request);

        assertThat(result.token()).isEqualTo("jwt_token");
        verify(authenticationManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("Should throw BadCredentialsException when credentials are invalid")
    void shouldThrowException_whenCredentialsAreInvalid() {
        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager).authenticate(any());

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BadCredentialsException.class);

        verify(jwtService, never()).generateToken(any());
    }
}