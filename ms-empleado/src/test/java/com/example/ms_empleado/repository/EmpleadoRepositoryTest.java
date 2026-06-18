package com.example.ms_empleado.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import net.datafaker.Faker;

import com.example.ms_empleado.model.Empleado;

@DataJpaTest
@ActiveProfiles("test")
class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository repository;

    @Test
    void guardarYValidarExistenciaPorRutYCorreo() {
        Faker faker = new Faker();
        String correo = faker.internet().emailAddress();

        repository.save(new Empleado(null, "33333333-3", faker.name().firstName(), faker.name().lastName(), correo, "977777777", "Bodega", "Noche", BigDecimal.valueOf(650000), true));

        assertThat(repository.existsByRut("33333333-3")).isTrue();
        assertThat(repository.existsByCorreo(correo)).isTrue();
    }
}
