package com.example.ms_devolucion.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.ms_devolucion.dto.ApiResponse;
import com.example.ms_devolucion.dto.DevolucionDTO;
import com.example.ms_devolucion.model.Devolucion;
import com.example.ms_devolucion.service.DevolucionService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class DevolucionControllerTest {

    private final DevolucionService service = mock(DevolucionService.class);
    private final DevolucionController controller = new DevolucionController(service);

    @Test
    void crearRetornaCreated() {
        Devolucion devolucion = new Devolucion(1L, 1L, 1L, "Producto danado", "SOLICITADA");
        when(service.crear(any(DevolucionDTO.class))).thenReturn(devolucion);

        ResponseEntity<ApiResponse<Devolucion>> response = controller.crear(new DevolucionDTO());

        assertEquals(201, response.getStatusCode().value());
        assertTrue(response.getBody().isSuccess());
        assertEquals(devolucion, response.getBody().getData());
    }
}
