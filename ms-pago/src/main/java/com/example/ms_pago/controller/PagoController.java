package com.example.ms_pago.controller;

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

import com.example.ms_pago.dto.ApiResponse;
import com.example.ms_pago.dto.PagoDTO;
import com.example.ms_pago.model.Pago;
import com.example.ms_pago.service.PagoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Pago>> crear(@Valid @RequestBody PagoDTO dto) {
        return ResponseEntity.status(201).body(
                ApiResponse.<Pago>builder()
                        .success(true)
                        .message("Pago creado")
                        .data(service.crear(dto))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Pago>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Pago>>builder()
                        .success(true)
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Pago>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Pago>builder()
                        .success(true)
                        .data(service.obtener(id))
                        .build()
        );
    }

    @GetMapping("/pedido/{pedidoId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Pago>>> listarPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(
                ApiResponse.<List<Pago>>builder()
                        .success(true)
                        .data(service.listarPorPedido(pedidoId))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Pago>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PagoDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<Pago>builder()
                        .success(true)
                        .data(service.actualizar(id, dto))
                        .build()
        );
    }

    @PutMapping("/{id}/aprobar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Pago>> aprobar(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Pago>builder()
                        .success(true)
                        .message("Pago aprobado")
                        .data(service.aprobar(id))
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
                        .message("Pago eliminado")
                        .build()
        );
    }
}
