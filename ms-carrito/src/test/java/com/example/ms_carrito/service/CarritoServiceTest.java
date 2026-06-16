package com.example.ms_carrito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_carrito.client.ClienteClient;
import com.example.ms_carrito.client.ProductoClient;
import com.example.ms_carrito.dto.CarritoDTO;
import com.example.ms_carrito.dto.CarritoItemDTO;
import com.example.ms_carrito.model.Carrito;
import com.example.ms_carrito.repository.CarritoRepository;

@ExtendWith(MockitoExtension.class)
class CarritoServiceTest {

    @Mock
    private CarritoRepository repo;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private CarritoService service;

    @Test
    void crearCuandoClienteYProductoExistenDebeCalcularTotal() {
        CarritoDTO dto = dto();
        String token = "Bearer token";

        when(clienteClient.obtener(1L, token)).thenReturn(new Object());
        when(productoClient.obtener(2L, token)).thenReturn(new Object());
        when(repo.save(any(Carrito.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Carrito response = service.crear(dto, token);

        assertEquals(BigDecimal.valueOf(3000), response.getTotal());
        assertEquals(1, response.getItems().size());
        verify(repo).save(any(Carrito.class));
    }

    @Test
    void crearCuandoClienteNoExisteDebeLanzarExcepcion() {
        CarritoDTO dto = dto();
        String token = "Bearer token";

        when(clienteClient.obtener(1L, token)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> service.crear(dto, token));
        verify(repo, never()).save(any(Carrito.class));
    }

    private CarritoDTO dto() {
        CarritoItemDTO item = new CarritoItemDTO();
        item.setProductoId(2L);
        item.setCantidad(2);
        item.setPrecioUnitario(BigDecimal.valueOf(1500));

        CarritoDTO dto = new CarritoDTO();
        dto.setClienteId(1L);
        dto.setEstado("ABIERTO");
        dto.setItems(List.of(item));
        return dto;
    }
}
