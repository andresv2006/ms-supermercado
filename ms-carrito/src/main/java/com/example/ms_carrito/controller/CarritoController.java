package com.example.ms_carrito.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_carrito.dto.ApiResponse;
import com.example.ms_carrito.dto.CarritoDTO;
import com.example.ms_carrito.model.Carrito;
import com.example.ms_carrito.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
@Tag(name = "Carritos", description = "Gestion de carritos con validacion REST de cliente y producto")
public class CarritoController {

    private final CarritoService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Crear carrito", description = "Crea un carrito y valida cliente/producto consultando otros microservicios")
    public ResponseEntity<ApiResponse<Carrito>> crear(
            @Valid @RequestBody CarritoDTO dto,
            @Parameter(description = "Token JWT") @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<Carrito>builder()
                        .success(true)
                        .message("Carrito creado")
                        .data(service.crear(dto, token))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Listar carritos", description = "Obtiene todos los carritos registrados")
    public ResponseEntity<ApiResponse<List<Carrito>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Carrito>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Obtener carrito", description = "Busca un carrito por su identificador")
    public ResponseEntity<ApiResponse<Carrito>> obtener(@Parameter(description = "ID del carrito") @PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Carrito>builder()
                        .success(true)
                        .message("Carrito obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Actualizar carrito", description = "Actualiza un carrito y vuelve a validar cliente/producto por REST")
    public ResponseEntity<ApiResponse<Carrito>> actualizar(
            @Parameter(description = "ID del carrito") @PathVariable Long id,
            @Valid @RequestBody CarritoDTO dto,
            @Parameter(description = "Token JWT") @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<Carrito>builder()
                        .success(true)
                        .message("Carrito actualizado")
                        .data(service.actualizar(id, dto, token))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar carrito", description = "Elimina un carrito registrado")
    public ResponseEntity<ApiResponse<Void>> eliminar(@Parameter(description = "ID del carrito") @PathVariable Long id) {
        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Carrito eliminado")
                        .build()
        );
    }
}
