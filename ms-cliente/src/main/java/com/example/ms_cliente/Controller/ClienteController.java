package com.example.ms_cliente.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_cliente.dto.ApiResponse;
import com.example.ms_cliente.dto.ClienteDTO;
import com.example.ms_cliente.model.Cliente;
import com.example.ms_cliente.service.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Cliente>> crear(@Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.status(201).body(ApiResponse.<Cliente>builder().success(true).message("Cliente creado").data(service.crear(dto)).build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Cliente>>> listar() {
        return ResponseEntity.ok(ApiResponse.<List<Cliente>>builder().success(true).message("Listado obtenido").data(service.listar()).build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Cliente>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<Cliente>builder().success(true).message("Cliente obtenido").data(service.obtener(id)).build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Cliente>> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(ApiResponse.<Cliente>builder().success(true).message("Cliente actualizado").data(service.actualizar(id, dto)).build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().success(true).message("Cliente eliminado").build());
    }
}
