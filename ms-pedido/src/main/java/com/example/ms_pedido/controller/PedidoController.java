package com.example.ms_pedido.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_pedido.dto.ApiResponse;
import com.example.ms_pedido.dto.PedidoDTO;
import com.example.ms_pedido.model.Pedido;
import com.example.ms_pedido.service.PedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Pedido>> crear(
            @Valid @RequestBody PedidoDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<Pedido>builder()
                        .success(true)
                        .message("Pedido creado")
                        .data(service.crear(dto, token))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Pedido>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Pedido>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Pedido>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Pedido>builder()
                        .success(true)
                        .message("Pedido obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Pedido>> actualizarEstado(@PathVariable Long id,
                                                                @RequestParam String estado) {
        return ResponseEntity.ok(
                ApiResponse.<Pedido>builder()
                        .success(true)
                        .message("Estado actualizado")
                        .data(service.actualizarEstado(id, estado))
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
                        .message("Pedido eliminado")
                        .build()
        );
    }
}
