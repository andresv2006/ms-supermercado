package com.example.ms_producto.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.ms_producto.dto.ApiResponse;
import com.example.ms_producto.dto.ProductoDTO;
import com.example.ms_producto.model.Producto;
import com.example.ms_producto.service.ProductoService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class ProductoControllerTest {

    private final ProductoService service = mock(ProductoService.class);
    private final ProductoController controller = new ProductoController(service);

    @Test
    void crearRetornaCreated() {
        Producto producto = new Producto(1L, "SKU-1", "Arroz", "Bolsa 1kg", BigDecimal.valueOf(1500), 1L, true);
        when(service.crear(any(ProductoDTO.class), anyString())).thenReturn(producto);

        ResponseEntity<ApiResponse<Producto>> response = controller.crear(new ProductoDTO(), "Bearer token");

        assertEquals(201, response.getStatusCode().value());
        assertTrue(response.getBody().isSuccess());
    }
}