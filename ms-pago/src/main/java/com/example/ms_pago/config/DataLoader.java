package com.example.ms_pago.config;

import com.example.ms_pago.model.Pago;
import com.example.ms_pago.repository.PagoRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PagoRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        repository.save(new Pago(null, 1L, "TARJETA", BigDecimal.valueOf(15000), "PENDIENTE"));
    }
}
