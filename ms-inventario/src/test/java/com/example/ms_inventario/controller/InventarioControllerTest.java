package com.example.ms_inventario.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.ms_inventario.dto.ApiResponse;
import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.service.InventarioService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class InventarioControllerTest {

    private final InventarioService service = mock(InventarioService.class);
    private final InventarioController controller = new InventarioController(service);

    @Test
    void crearRetornaCreated() {
        Inventario inventario = new Inventario(1L, 1L, 20, 5, "Bodega A");
        when(service.crear(any(InventarioDTO.class))).thenReturn(inventario);

        ResponseEntity<ApiResponse<Inventario>> response = controller.crear(new InventarioDTO());

        assertEquals(201, response.getStatusCode().value());
        assertTrue(response.getBody().isSuccess());
        assertEquals(inventario, response.getBody().getData());
    }
}
