package com.example.ms_producto.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.ms_producto.model.Producto;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository repository;

    @Test
    void guardaProducto() {
        Producto producto = new Producto(null, "SKU-1", "Arroz", "Bolsa 1kg", BigDecimal.valueOf(1500), 1L, true);

        Producto guardado = repository.save(producto);

        assertNotNull(guardado.getId());
        assertEquals("Arroz", guardado.getNombre());
    }
}