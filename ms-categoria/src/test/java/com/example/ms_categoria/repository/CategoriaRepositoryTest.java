package com.example.ms_categoria.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import net.datafaker.Faker;

import com.example.ms_categoria.model.Categoria;

@DataJpaTest
@ActiveProfiles("test")
class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository repository;

    @Test
    void guardarYBuscarCategoria() {
        Faker faker = new Faker();
        String nombre = faker.commerce().department();

        Categoria categoria = repository.save(new Categoria(null, nombre, "Categoria generada para prueba", true));

        assertThat(categoria.getId()).isNotNull();
        assertThat(repository.findById(categoria.getId())).isPresent();
    }
}
