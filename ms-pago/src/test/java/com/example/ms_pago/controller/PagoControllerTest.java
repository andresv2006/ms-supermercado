package com.example.ms_pago.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.ms_pago.dto.ApiResponse;
import com.example.ms_pago.dto.PagoDTO;
import com.example.ms_pago.model.Pago;
import com.example.ms_pago.service.PagoService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class PagoControllerTest {

    private final PagoService service = mock(PagoService.class);
    private final PagoController controller = new PagoController(service);

    @Test
    void crearRetornaCreated() {
        Pago pago = new Pago(1L, 1L, "TARJETA", BigDecimal.valueOf(10000), "PENDIENTE");
        when(service.crear(any(PagoDTO.class))).thenReturn(pago);

        ResponseEntity<ApiResponse<Pago>> response = controller.crear(new PagoDTO());

        assertEquals(201, response.getStatusCode().value());
        assertTrue(response.getBody().isSuccess());
        assertEquals(pago, response.getBody().getData());
    }
}
