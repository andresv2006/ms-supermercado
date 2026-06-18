package com.example.ms_carrito.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import com.example.ms_carrito.security.JwtUtil;

import com.example.ms_carrito.dto.CarritoDTO;
import com.example.ms_carrito.model.Carrito;
import com.example.ms_carrito.model.CarritoItem;
import com.example.ms_carrito.service.CarritoService;

@WebMvcTest(controllers = CarritoController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class })
@AutoConfigureMockMvc(addFilters = false)
class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private CarritoService service;

    @Test
    void listarRetornaCarritos() throws Exception {
        Carrito carrito = new Carrito(1L, 10L, "ABIERTO", BigDecimal.valueOf(3000), List.of());
        when(service.listar()).thenReturn(List.of(carrito));

        mockMvc.perform(get("/api/v1/carritos/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado obtenido"));
    }

    @Test
    void crearRetornaCarritoCreado() throws Exception {
        CarritoItem item = new CarritoItem(1L, 2L, 3, BigDecimal.valueOf(1000), null);
        Carrito carrito = new Carrito(1L, 10L, "ABIERTO", BigDecimal.valueOf(3000), List.of(item));
        when(service.crear(any(CarritoDTO.class), eq("Bearer token"))).thenReturn(carrito);

        mockMvc.perform(post("/api/v1/carritos/")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clienteId\":10,\"estado\":\"ABIERTO\",\"items\":[{\"productoId\":2,\"cantidad\":3,\"precioUnitario\":1000}]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Carrito creado"))
                .andExpect(jsonPath("$.data.clienteId").value(10));
    }
}
