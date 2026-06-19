package com.example.ms_cliente.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.ms_cliente.dto.ClienteDTO;
import com.example.ms_cliente.model.Cliente;
import com.example.ms_cliente.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Test
    void crearGuardaCliente() {
        when(repository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente creado = service.crear(dto());

        assertEquals("11111111-1", creado.getRut());
        assertTrue(creado.getActivo());
        verify(repository).save(any(Cliente.class));
    }

    @Test
    void obtenerLanzaExcepcionSiNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L));
    }

    private ClienteDTO dto() {
        ClienteDTO dto = new ClienteDTO();
        dto.setRut("11111111-1");
        dto.setNombre("Ana");
        dto.setApellido("Perez");
        dto.setCorreo("ana@test.cl");
        dto.setTelefono("999999999");
        dto.setDireccion("Av Siempre Viva");
        dto.setActivo(true);
        return dto;
    }
}