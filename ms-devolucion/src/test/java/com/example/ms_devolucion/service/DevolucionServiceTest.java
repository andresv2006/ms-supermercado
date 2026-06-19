package com.example.ms_devolucion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ms_devolucion.dto.DevolucionDTO;
import com.example.ms_devolucion.model.Devolucion;
import com.example.ms_devolucion.repository.DevolucionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DevolucionServiceTest {

    @Mock
    private DevolucionRepository repository;

    @InjectMocks
    private DevolucionService service;

    @Test
    void crearNormalizaEstadoYGuardaDevolucion() {
        when(repository.save(any(Devolucion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Devolucion devolucion = service.crear(dto("solicitada"));

        assertEquals("SOLICITADA", devolucion.getEstado());
        verify(repository).save(any(Devolucion.class));
    }

    @Test
    void rechazarCambiaEstado() {
        Devolucion devolucion = new Devolucion(1L, 1L, 1L, "Producto danado", "SOLICITADA");
        when(repository.findById(1L)).thenReturn(Optional.of(devolucion));
        when(repository.save(any(Devolucion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Devolucion rechazada = service.rechazar(1L);

        assertEquals("RECHAZADA", rechazada.getEstado());
    }

    @Test
    void obtenerLanzaExcepcionSiNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L));
    }

    private DevolucionDTO dto(String estado) {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setPedidoId(1L);
        dto.setPagoId(1L);
        dto.setMotivo("Producto danado");
        dto.setEstado(estado);
        return dto;
    }
}
