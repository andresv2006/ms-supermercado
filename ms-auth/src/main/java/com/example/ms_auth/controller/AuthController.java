package com.example.ms_auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_auth.dto.*;
import com.example.ms_auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autenticacion", description = "Endpoints para registro, login y renovacion de token")
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Crea un usuario y devuelve tokens JWT para consumir los microservicios protegidos")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder().success(true).message("Usuario registrado").data(service.register(req)).build());
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion", description = "Valida credenciales y entrega token de acceso y refresh token")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder().success(true).message("Login exitoso").data(service.login(req)).build());
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar token", description = "Genera un nuevo token de acceso usando un refresh token valido")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@Valid @RequestBody RefreshRequest req) {
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder().success(true).message("Token renovado").data(service.refresh(req.getRefreshToken())).build());
    }
}
