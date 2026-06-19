package com.example.ms_pedido.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.example.ms_pedido.client.ClienteClient;
import com.example.ms_pedido.client.ProductoClient;
import com.example.ms_pedido.dto.PedidoDTO;
import com.example.ms_pedido.dto.PedidoDetalleDTO;
import com.example.ms_pedido.model.Pedido;
import com.example.ms_pedido.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private PedidoService service;

    @Test
    void crearCalculaTotalYGuardaPedido() {
        when(clienteClient.obtener(eq(1L), anyString())).thenReturn(new Object());
        when(productoClient.obtener(eq(1L), anyString())).thenReturn(new Object());
        when(repository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedido pedido = service.crear(dto(), "Bearer token");

        assertEquals(BigDecimal.valueOf(3000), pedido.getTotal());
        assertEquals(1, pedido.getDetalles().size());
        verify(repository).save(any(Pedido.class));
    }

    @Test
    void obtenerLanzaExcepcionSiNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L));
    }

    private PedidoDTO dto() {
        PedidoDetalleDTO detalle = PedidoDetalleDTO.builder()
                .productoId(1L)
                .cantidad(2)
                .precioUnitario(BigDecimal.valueOf(1500))
                .build();
        return PedidoDTO.builder()
                .clienteId(1L)
                .estado("CREADO")
                .detalles(List.of(detalle))
                .build();
    }
}