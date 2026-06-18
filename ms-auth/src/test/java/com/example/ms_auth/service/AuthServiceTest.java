package com.example.ms_auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.ms_auth.dto.AuthResponse;
import com.example.ms_auth.dto.LoginRequest;
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
        RegisterRequest req = register("admin", "admin123", "ROLE_ADMIN");

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
    void registerCuandoRoleVieneVacioDebeUsarRoleUser() {
        RegisterRequest req = register("cliente", "clave123", "");

        when(usuarioRepo.existsByUsername("cliente")).thenReturn(false);
        when(encoder.encode("clave123")).thenReturn("pass-cifrada");
        when(jwtUtil.generarToken("cliente", "ROLE_USER")).thenReturn("access-user");
        when(jwtUtil.generarRefreshToken("cliente")).thenReturn("refresh-user");

        AuthResponse response = service.register(req);

        assertEquals("access-user", response.getAccessToken());
        verify(usuarioRepo).save(any(Usuario.class));
    }

    @Test
    void registerCuandoUsernameExisteDebeLanzarExcepcion() {
        RegisterRequest req = register("admin", "admin123", null);
        when(usuarioRepo.existsByUsername("admin")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.register(req));
        verify(usuarioRepo, never()).save(any(Usuario.class));
    }

    @Test
    void loginCuandoCredencialesSonValidasDebeRetornarTokens() {
        LoginRequest req = new LoginRequest();
        req.setUsername("admin");
        req.setPassword("admin123");
        Usuario user = usuario("admin", "ROLE_ADMIN");

        when(usuarioRepo.findByUsername("admin")).thenReturn(Optional.of(user));
        when(jwtUtil.generarToken("admin", "ROLE_ADMIN")).thenReturn("access-token");
        when(jwtUtil.generarRefreshToken("admin")).thenReturn("refresh-token");

        AuthResponse response = service.login(req);

        assertEquals("access-token", response.getAccessToken());
        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(refreshRepo).save(any(RefreshToken.class));
    }

    @Test
    void loginCuandoUsuarioNoExisteDebeLanzarExcepcion() {
        LoginRequest req = new LoginRequest();
        req.setUsername("nadie");
        req.setPassword("admin123");

        when(usuarioRepo.findByUsername("nadie")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.login(req));
    }

    @Test
    void refreshCuandoTokenEsValidoDebeCrearNuevoAccessToken() {
        RefreshToken token = new RefreshToken();
        token.setToken("refresh-token");
        token.setUsername("admin");
        token.setExpiryDate(new Date(System.currentTimeMillis() + 60000));
        Usuario user = usuario("admin", "ROLE_ADMIN");

        when(refreshRepo.findByToken("refresh-token")).thenReturn(Optional.of(token));
        when(jwtUtil.esValido("refresh-token")).thenReturn(true);
        when(jwtUtil.esRefreshToken("refresh-token")).thenReturn(true);
        when(usuarioRepo.findByUsername("admin")).thenReturn(Optional.of(user));
        when(jwtUtil.generarToken("admin", "ROLE_ADMIN")).thenReturn("new-access");

        AuthResponse response = service.refresh("refresh-token");

        assertEquals("new-access", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
    }

    @Test
    void refreshCuandoNoExisteDebeLanzarExcepcion() {
        when(refreshRepo.findByToken("bad")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.refresh("bad"));
    }

    @Test
    void refreshCuandoEstaVencidoDebeLanzarExcepcion() {
        RefreshToken token = new RefreshToken();
        token.setToken("refresh-token");
        token.setUsername("admin");
        token.setExpiryDate(new Date(System.currentTimeMillis() - 60000));

        when(refreshRepo.findByToken("refresh-token")).thenReturn(Optional.of(token));
        when(jwtUtil.esValido("refresh-token")).thenReturn(true);
        when(jwtUtil.esRefreshToken("refresh-token")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.refresh("refresh-token"));
    }

    private RegisterRequest register(String username, String password, String role) {
        RegisterRequest req = new RegisterRequest();
        req.setUsername(username);
        req.setPassword(password);
        req.setRole(role);
        return req;
    }

    private Usuario usuario(String username, String role) {
        Usuario user = new Usuario();
        user.setUsername(username);
        user.setRole(role);
        user.setPassword("pass");
        return user;
    }
}