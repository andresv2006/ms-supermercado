package com.example.ms_carrito.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import net.datafaker.Faker;

import com.example.ms_carrito.model.Carrito;

@DataJpaTest
@ActiveProfiles("test")
class CarritoRepositoryTest {

    @Autowired
    private CarritoRepository repository;

    @Test
    void guardarYBuscarCarrito() {
        Faker faker = new Faker();
        Long clienteId = faker.number().numberBetween(1L, 50L);

        Carrito carrito = repository.save(new Carrito(null, clienteId, "ABIERTO", BigDecimal.ZERO, new ArrayList<>()));

        assertThat(carrito.getId()).isNotNull();
        assertThat(repository.findById(carrito.getId())).isPresent();
    }
}
