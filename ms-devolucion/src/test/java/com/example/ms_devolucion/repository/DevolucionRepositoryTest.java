package com.example.ms_devolucion.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.ms_devolucion.model.Devolucion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class DevolucionRepositoryTest {

    @Autowired
    private DevolucionRepository repository;

    @Test
    void buscaPorPedidoYPago() {
        repository.save(new Devolucion(null, 1L, 2L, "Producto danado", "SOLICITADA"));

        assertEquals(1, repository.findByPedidoId(1L).size());
        assertEquals(1, repository.findByPagoId(2L).size());
    }
}
