package com.example.ms_inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_inventario.dto.ApiResponse;
import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.service.InventarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Inventario>> crear(@Valid @RequestBody InventarioDTO dto) {
        return ResponseEntity.status(201).body(
                ApiResponse.<Inventario>builder()
                        .success(true)
                        .message("Inventario creado")
                        .data(service.crear(dto))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Inventario>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Inventario>>builder()
                        .success(true)
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Inventario>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Inventario>builder()
                        .success(true)
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Inventario>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody InventarioDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<Inventario>builder()
                        .success(true)
                        .data(service.actualizar(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Inventario eliminado")
                        .build()
        );
    }
}
