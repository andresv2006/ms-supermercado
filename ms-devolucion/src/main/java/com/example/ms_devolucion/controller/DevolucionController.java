package com.example.ms_devolucion.controller;

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

import com.example.ms_devolucion.dto.ApiResponse;
import com.example.ms_devolucion.dto.DevolucionDTO;
import com.example.ms_devolucion.model.Devolucion;
import com.example.ms_devolucion.service.DevolucionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/devoluciones")
@RequiredArgsConstructor
public class DevolucionController {

    private final DevolucionService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Devolucion>> crear(@Valid @RequestBody DevolucionDTO dto) {
        return ResponseEntity.status(201).body(
                ApiResponse.<Devolucion>builder()
                        .success(true)
                        .message("Devolucion creada")
                        .data(service.crear(dto))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Devolucion>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Devolucion>>builder()
                        .success(true)
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Devolucion>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Devolucion>builder()
                        .success(true)
                        .data(service.obtener(id))
                        .build()
        );
    }

    @GetMapping("/pedido/{pedidoId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Devolucion>>> listarPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(
                ApiResponse.<List<Devolucion>>builder()
                        .success(true)
                        .data(service.listarPorPedido(pedidoId))
                        .build()
        );
    }

    @GetMapping("/pago/{pagoId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Devolucion>>> listarPorPago(@PathVariable Long pagoId) {
        return ResponseEntity.ok(
                ApiResponse.<List<Devolucion>>builder()
                        .success(true)
                        .data(service.listarPorPago(pagoId))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Devolucion>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DevolucionDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<Devolucion>builder()
                        .success(true)
                        .data(service.actualizar(id, dto))
                        .build()
        );
    }

    @PutMapping("/{id}/aprobar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Devolucion>> aprobar(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Devolucion>builder()
                        .success(true)
                        .message("Devolucion aprobada")
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
                        .message("Devolucion eliminada")
                        .build()
        );
    }
}
