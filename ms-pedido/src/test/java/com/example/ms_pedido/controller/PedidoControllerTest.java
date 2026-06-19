package com.example.ms_pedido.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.ms_pedido.dto.ApiResponse;
import com.example.ms_pedido.dto.PedidoDTO;
import com.example.ms_pedido.model.Pedido;
import com.example.ms_pedido.service.PedidoService;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class PedidoControllerTest {

    private final PedidoService service = mock(PedidoService.class);
    private final PedidoController controller = new PedidoController(service);

    @Test
    void crearRetornaCreated() {
        Pedido pedido = new Pedido(1L, 1L, "CREADO", BigDecimal.valueOf(3000), new ArrayList<>());
        when(service.crear(any(PedidoDTO.class), anyString())).thenReturn(pedido);

        ResponseEntity<ApiResponse<Pedido>> response = controller.crear(new PedidoDTO(), "Bearer token");

        assertEquals(201, response.getStatusCode().value());
        assertTrue(response.getBody().isSuccess());
    }
}