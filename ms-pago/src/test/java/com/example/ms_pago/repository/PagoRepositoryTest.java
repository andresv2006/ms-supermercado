package com.example.ms_pago.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.ms_pago.model.Pago;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class PagoRepositoryTest {

    @Autowired
    private PagoRepository repository;

    @Test
    void buscaPagosPorPedido() {
        repository.save(new Pago(null, 1L, "TARJETA", BigDecimal.valueOf(10000), "PENDIENTE"));

        assertEquals(1, repository.findByPedidoId(1L).size());
    }
}
