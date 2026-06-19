package com.example.ms_cliente.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.ms_cliente.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    @Test
    void guardaCliente() {
        Cliente cliente = new Cliente(null, "11111111-1", "Ana", "Perez", "ana@test.cl", "999999999", "Direccion", true);

        Cliente guardado = repository.save(cliente);

        assertNotNull(guardado.getId());
        assertEquals("Ana", guardado.getNombre());
    }
}