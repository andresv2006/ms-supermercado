package com.example.ms_auth.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.ms_auth.model.Usuario;
import com.example.ms_auth.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            usuarioRepository.save(admin);
        }

        Faker faker = new Faker();

        for (int i = 0; i < 3; i++) {
            String username = faker.name().username();
            if (usuarioRepository.existsByUsername(username)) {
                continue;
            }

            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setPassword(passwordEncoder.encode("user123"));
            usuario.setRole("ROLE_USER");
            usuarioRepository.save(usuario);
        }
    }
}