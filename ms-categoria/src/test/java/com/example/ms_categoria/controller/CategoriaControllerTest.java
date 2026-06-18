package com.example.ms_categoria.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.example.ms_categoria.security.JwtUtil;

import com.example.ms_categoria.dto.CategoriaDTO;
import com.example.ms_categoria.model.Categoria;
import com.example.ms_categoria.service.CategoriaService;

@WebMvcTest(controllers = CategoriaController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class })
@AutoConfigureMockMvc(addFilters = false)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private CategoriaService service;

    @Test
    void listarRetornaCategorias() throws Exception {
        when(service.listar()).thenReturn(List.of(new Categoria(1L, "Abarrotes", "Productos basicos", true)));

        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado obtenido"));
    }

    @Test
    void crearRetornaCategoriaCreada() throws Exception {
        when(service.crear(any(CategoriaDTO.class))).thenReturn(new Categoria(1L, "Lacteos", "Productos refrigerados", true));

        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Lacteos\",\"descripcion\":\"Productos refrigerados\",\"activo\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Categoria creado"))
                .andExpect(jsonPath("$.data.nombre").value("Lacteos"));
    }
}
