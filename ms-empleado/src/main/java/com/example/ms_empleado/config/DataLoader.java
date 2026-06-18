package com.example.ms_empleado.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.ms_empleado.model.Empleado;
import com.example.ms_empleado.repository.EmpleadoRepository;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepository;

    @Override
    public void run(String... args) {
        if (empleadoRepository.count() >= 10) {
            return;
        }

        Faker faker = new Faker();
        String[] cargos = { "Cajero", "Reponedor", "Supervisor", "Bodega", "Atencion cliente" };
        String[] turnos = { "Manana", "Tarde", "Noche", "Completo" };

        for (int i = 0; i < 7; i++) {
            Empleado empleado = new Empleado();
            empleado.setRut(faker.idNumber().valid());
            empleado.setNombre(faker.name().firstName());
            empleado.setApellido(faker.name().lastName());
            empleado.setCorreo(faker.internet().emailAddress());
            empleado.setTelefono(String.valueOf(faker.number().numberBetween(900000000, 999999999)));
            empleado.setCargo(faker.options().option(cargos));
            empleado.setTurno(faker.options().option(turnos));
            empleado.setSueldo(BigDecimal.valueOf(faker.number().numberBetween(500000, 950000)));
            empleado.setActivo(true);
            empleadoRepository.save(empleado);
        }
    }
}