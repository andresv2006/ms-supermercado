package com.example.ms_inventario.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.ms_inventario.model.Inventario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class InventarioRepositoryTest {

    @Autowired
    private InventarioRepository repository;

    @Test
    void buscaPorProductoId() {
        repository.save(new Inventario(null, 1L, 20, 5, "Bodega A"));

        assertTrue(repository.findByProductoId(1L).isPresent());
    }
}
