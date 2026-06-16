package com.example.ms_auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.ms_auth.dto.AuthResponse;
import com.example.ms_auth.dto.RegisterRequest;
import com.example.ms_auth.model.RefreshToken;
import com.example.ms_auth.model.Usuario;
import com.example.ms_auth.repository.RefreshTokenRepository;
import com.example.ms_auth.repository.UsuarioRepository;
import com.example.ms_auth.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepo;

    @Mock
    private RefreshTokenRepository refreshRepo;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService service;

    @Test
    void registerCuandoUsernameDisponibleDebeCrearUsuarioYTokens() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("admin");
        req.setPassword("admin123");
        req.setRole("ROLE_ADMIN");

        when(usuarioRepo.existsByUsername("admin")).thenReturn(false);
        when(encoder.encode("admin123")).thenReturn("pass-cifrada");
        when(jwtUtil.generarToken("admin", "ROLE_ADMIN")).thenReturn("access-token");
        when(jwtUtil.generarRefreshToken("admin")).thenReturn("refresh-token");

        AuthResponse response = service.register(req);

        assertNotNull(response);
        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
        verify(usuarioRepo).save(any(Usuario.class));
        verify(refreshRepo).save(any(RefreshToken.class));
    }

    @Test
    void registerCuandoUsernameExisteDebeLanzarExcepcion() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("admin");
        req.setPassword("admin123");

        when(usuarioRepo.existsByUsername("admin")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.register(req));
        verify(usuarioRepo, never()).save(any(Usuario.class));
    }
}
