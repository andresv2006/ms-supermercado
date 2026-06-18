package com.example.ms_empleado.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
@Tag(name = "Empleados", description = "Gestion de empleados del supermercado")
public class EmpleadoController {

    private final EmpleadoService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear empleado", description = "Registra un empleado con sus datos laborales")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Empleado creado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud invalida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token invalido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<ApiResponse<EntityModel<Empleado>>> crear(@Valid @RequestBody EmpleadoDTO dto) {

        Empleado empleado = service.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<EntityModel<Empleado>>builder()
                        .success(true)
                        .message("Empleado creado")
                        .data(agregarLinks(empleado))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Listar empleados", description = "Obtiene todos los empleados registrados")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token invalido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<ApiResponse<CollectionModel<EntityModel<Empleado>>>> listar() {
        List<EntityModel<Empleado>> empleados = service.listar().stream()
                .map(this::agregarLinks)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Empleado>> collection = CollectionModel.of(empleados,
                linkTo(methodOn(EmpleadoController.class).listar()).withSelfRel());

        return ResponseEntity.ok(
                ApiResponse.<CollectionModel<EntityModel<Empleado>>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(collection)
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Obtener empleado", description = "Busca un empleado por su identificador")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Empleado obtenido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token invalido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<ApiResponse<EntityModel<Empleado>>> obtener(@Parameter(description = "ID del empleado") @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<EntityModel<Empleado>>builder()
                        .success(true)
                        .message("Empleado obtenido")
                        .data(agregarLinks(service.obtener(id)))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar empleado", description = "Modifica los datos de un empleado existente")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Empleado actualizado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud invalida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token invalido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<ApiResponse<EntityModel<Empleado>>> actualizar(@Parameter(description = "ID del empleado") @PathVariable Long id,
                                                            @Valid @RequestBody EmpleadoDTO dto) {

        Empleado empleado = service.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<EntityModel<Empleado>>builder()
                        .success(true)
                        .message("Empleado actualizado")
                        .data(agregarLinks(empleado))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado registrado")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Empleado eliminado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token invalido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<ApiResponse<Void>> eliminar(@Parameter(description = "ID del empleado") @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity
         .noContent()
         .build();
    }

    private EntityModel<Empleado> agregarLinks(Empleado empleado) {
        return EntityModel.of(empleado,
                linkTo(methodOn(EmpleadoController.class).obtener(empleado.getId())).withSelfRel(),
                linkTo(methodOn(EmpleadoController.class).listar()).withRel("empleados"),
                linkTo(methodOn(EmpleadoController.class).actualizar(empleado.getId(), null)).withRel("actualizar"),
                linkTo(methodOn(EmpleadoController.class).eliminar(empleado.getId())).withRel("eliminar"));
    }
}
