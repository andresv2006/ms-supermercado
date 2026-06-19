package com.example.ms_cliente.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.ms_cliente.dto.ApiResponse;
import com.example.ms_cliente.dto.ClienteDTO;
import com.example.ms_cliente.model.Cliente;
import com.example.ms_cliente.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class ClienteControllerTest {

    private final ClienteService service = mock(ClienteService.class);
    private final ClienteController controller = new ClienteController(service);

    @Test
    void crearRetornaCreated() {
        Cliente cliente = new Cliente(1L, "11111111-1", "Ana", "Perez", "ana@test.cl", "999999999", "Direccion", true);
        when(service.crear(any(ClienteDTO.class))).thenReturn(cliente);

        ResponseEntity<ApiResponse<Cliente>> response = controller.crear(new ClienteDTO());

        assertEquals(201, response.getStatusCode().value());
        assertTrue(response.getBody().isSuccess());
        assertEquals(1L, response.getBody().getData().getId());
    }
}