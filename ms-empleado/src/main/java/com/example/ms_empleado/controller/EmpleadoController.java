package com.example.ms_empleado.controller;

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

import com.example.ms_empleado.dto.ApiResponse;
import com.example.ms_empleado.dto.EmpleadoDTO;
import com.example.ms_empleado.model.Empleado;
import com.example.ms_empleado.service.EmpleadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
@Tag(name = "Empleados", description = "Gestion de empleados del supermercado")
public class EmpleadoController {

    private final EmpleadoService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear empleado", description = "Registra un empleado con sus datos laborales")
    public ResponseEntity<ApiResponse<Empleado>> crear(@Valid @RequestBody EmpleadoDTO dto) {

        Empleado empleado = service.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<Empleado>builder()
                        .success(true)
                        .message("Empleado creado")
                        .data(empleado)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Listar empleados", description = "Obtiene todos los empleados registrados")
    public ResponseEntity<ApiResponse<List<Empleado>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Empleado>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Obtener empleado", description = "Busca un empleado por su identificador")
    public ResponseEntity<ApiResponse<Empleado>> obtener(@Parameter(description = "ID del empleado") @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Empleado>builder()
                        .success(true)
                        .message("Empleado obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar empleado", description = "Modifica los datos de un empleado existente")
    public ResponseEntity<ApiResponse<Empleado>> actualizar(@Parameter(description = "ID del empleado") @PathVariable Long id,
                                                            @Valid @RequestBody EmpleadoDTO dto) {

        Empleado empleado = service.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Empleado>builder()
                        .success(true)
                        .message("Empleado actualizado")
                        .data(empleado)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado registrado")
    public ResponseEntity<ApiResponse<Void>> eliminar(@Parameter(description = "ID del empleado") @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Empleado eliminado")
                        .build()
        );
    }
}
