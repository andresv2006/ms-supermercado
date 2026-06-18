package com.example.ms_carrito.config;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.ms_carrito.model.Carrito;
import com.example.ms_carrito.model.CarritoItem;
import com.example.ms_carrito.repository.CarritoRepository;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CarritoRepository carritoRepository;

    @Override
    public void run(String... args) {
        if (carritoRepository.count() >= 5) {
            return;
        }

        Faker faker = new Faker();

        for (int i = 0; i < 5; i++) {
            Carrito carrito = new Carrito();
            carrito.setClienteId(faker.number().numberBetween(1L, 20L));
            carrito.setEstado(faker.options().option("ABIERTO", "CERRADO"));
            carrito.setItems(new ArrayList<>());

            BigDecimal total = BigDecimal.ZERO;
            int cantidadItems = faker.number().numberBetween(1, 4);

            for (int j = 0; j < cantidadItems; j++) {
                CarritoItem item = new CarritoItem();
                item.setProductoId(faker.number().numberBetween(1L, 30L));
                item.setCantidad(faker.number().numberBetween(1, 6));
                item.setPrecioUnitario(BigDecimal.valueOf(faker.number().numberBetween(500, 10000)));
                item.setCarrito(carrito);
                carrito.getItems().add(item);
                total = total.add(item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())));
            }

            carrito.setTotal(total);
            carritoRepository.save(carrito);
        }
    }
}