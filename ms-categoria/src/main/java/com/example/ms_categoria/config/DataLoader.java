package com.example.ms_categoria.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.ms_categoria.model.Categoria;
import com.example.ms_categoria.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() >= 8) {
            return;
        }

        Faker faker = new Faker();

        for (int i = 0; i < 5; i++) {
            Categoria categoria = new Categoria();
            categoria.setNombre(faker.commerce().department() + " " + faker.number().numberBetween(1, 999));
            categoria.setDescripcion(faker.commerce().material() + " para supermercado");
            categoria.setActivo(true);
            categoriaRepository.save(categoria);
        }
    }
}