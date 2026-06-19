package com.example.ms_devolucion.config;

import com.example.ms_devolucion.model.Devolucion;
import com.example.ms_devolucion.repository.DevolucionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final DevolucionRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        repository.save(new Devolucion(null, 1L, 1L, "Producto danado", "SOLICITADA"));
    }
}
