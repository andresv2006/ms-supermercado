package com.example.ms_empleado.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ms_empleado.security.JwtUtil;

import com.example.ms_empleado.dto.EmpleadoDTO;
import com.example.ms_empleado.model.Empleado;
import com.example.ms_empleado.service.EmpleadoService;

@WebMvcTest(controllers = EmpleadoController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class })
@AutoConfigureMockMvc(addFilters = false)
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private EmpleadoService service;

    @Test
    void listarRetornaEmpleados() throws Exception {
        Empleado empleado = new Empleado(1L, "11111111-1", "Ana", "Perez", "ana@correo.cl", "999999999", "Cajera", "Manana", BigDecimal.valueOf(600000), true);
        when(service.listar()).thenReturn(List.of(empleado));

        mockMvc.perform(get("/api/v1/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado obtenido"));
    }

    @Test
    void crearRetornaEmpleadoCreado() throws Exception {
        Empleado empleado = new Empleado(1L, "22222222-2", "Luis", "Rojas", "luis@correo.cl", "988888888", "Supervisor", "Tarde", BigDecimal.valueOf(750000), true);
        when(service.crear(any(EmpleadoDTO.class))).thenReturn(empleado);

        mockMvc.perform(post("/api/v1/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rut\":\"22222222-2\",\"nombre\":\"Luis\",\"apellido\":\"Rojas\",\"correo\":\"luis@correo.cl\",\"telefono\":\"988888888\",\"cargo\":\"Supervisor\",\"turno\":\"Tarde\",\"sueldo\":750000,\"activo\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Empleado creado"))
                .andExpect(jsonPath("$.data.rut").value("22222222-2"));
    }
}
