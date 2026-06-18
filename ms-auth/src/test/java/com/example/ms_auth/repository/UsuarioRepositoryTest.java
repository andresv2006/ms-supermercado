package com.example.ms_auth.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import net.datafaker.Faker;

import com.example.ms_auth.model.Usuario;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    void guardarYBuscarPorUsername() {
        Faker faker = new Faker();
        String username = faker.name().username();

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword("1234");
        usuario.setRole("ROLE_ADMIN");
        repository.save(usuario);

        assertThat(repository.existsByUsername(username)).isTrue();
        assertThat(repository.findByUsername(username)).isPresent();
    }
}
