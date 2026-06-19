package com.example.ms_pedido.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.ms_pedido.model.Pedido;
import com.example.ms_pedido.model.PedidoDetalle;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository repository;

    @Test
    void guardaPedidoConDetalle() {
        Pedido pedido = new Pedido(null, 1L, "CREADO", BigDecimal.valueOf(3000), new ArrayList<>());
        pedido.addDetalle(new PedidoDetalle(null, 1L, 2, BigDecimal.valueOf(1500), pedido));

        Pedido guardado = repository.save(pedido);

        assertNotNull(guardado.getId());
        assertEquals(1, guardado.getDetalles().size());
    }
}