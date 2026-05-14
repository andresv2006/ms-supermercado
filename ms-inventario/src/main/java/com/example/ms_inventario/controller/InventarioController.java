package com.example.ms_inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_inventario.dto.ApiResponse;
import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.service.InventarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService service;

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
}
