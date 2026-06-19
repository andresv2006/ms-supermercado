package com.example.ms_cliente.config;

import com.example.ms_cliente.model.Cliente;
import com.example.ms_cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }
        Faker faker = new Faker();
        for (int i = 1; i <= 5; i++) {
            repository.save(new Cliente(null, "11.111.11" + i + "-" + i, faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), faker.phoneNumber().cellPhone(), faker.address().fullAddress(), true));
        }
    }
}