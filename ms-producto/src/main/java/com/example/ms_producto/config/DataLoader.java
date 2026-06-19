package com.example.ms_producto.config;

import com.example.ms_producto.model.Producto;
import com.example.ms_producto.repository.ProductoRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductoRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }
        Faker faker = new Faker();
        for (int i = 1; i <= 5; i++) {
            repository.save(new Producto(null, "SKU-" + i, faker.commerce().productName(), faker.commerce().material(), BigDecimal.valueOf(1000L * i), 1L, true));
        }
    }
}