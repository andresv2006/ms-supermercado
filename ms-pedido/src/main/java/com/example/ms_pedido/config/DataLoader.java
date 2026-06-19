package com.example.ms_pedido.config;

import com.example.ms_pedido.model.Pedido;
import com.example.ms_pedido.model.PedidoDetalle;
import com.example.ms_pedido.repository.PedidoRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PedidoRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }
        Pedido pedido = new Pedido(null, 1L, "CREADO", BigDecimal.valueOf(3000), new ArrayList<>());
        pedido.addDetalle(new PedidoDetalle(null, 1L, 2, BigDecimal.valueOf(1500), pedido));
        repository.save(pedido);
    }
}