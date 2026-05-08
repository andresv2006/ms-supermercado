package com.example.ms_auth.service;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ms_auth.dto.*;
import com.example.ms_auth.model.*;
import com.example.ms_auth.repository.*;
import com.example.ms_auth.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UsuarioRepository usuarioRepo;
    private final RefreshTokenRepository refreshRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest req) {
        if (usuarioRepo.existsByUsername(req.getUsername())) throw new IllegalArgumentException("El username ya existe");
        Usuario user = new Usuario();
        user.setUsername(req.getUsername());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(req.getRole() == null || req.getRole().isBlank() ? "ROLE_USER" : req.getRole());
        usuarioRepo.save(user);
        return generarRespuesta(user);
    }

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        Usuario user = usuarioRepo.findByUsername(req.getUsername()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return generarRespuesta(user);
    }

    public AuthResponse refresh(String refreshToken) {
        RefreshToken token = refreshRepo.findByToken(refreshToken).orElseThrow(() -> new RuntimeException("Refresh invalido"));
        if (!jwtUtil.esValido(refreshToken) || !jwtUtil.esRefreshToken(refreshToken) || token.getExpiryDate().before(new Date())) throw new RuntimeException("Refresh token invalido");
        Usuario user = usuarioRepo.findByUsername(token.getUsername()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new AuthResponse(jwtUtil.generarToken(user.getUsername(), user.getRole()), refreshToken);
    }

    private AuthResponse generarRespuesta(Usuario user) {
        String access = jwtUtil.generarToken(user.getUsername(), user.getRole());
        String refresh = jwtUtil.generarRefreshToken(user.getUsername());
        RefreshToken rt = new RefreshToken();
        rt.setToken(refresh);
        rt.setUsername(user.getUsername());
        rt.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        refreshRepo.save(rt);
        return new AuthResponse(access, refresh);
    }
}
