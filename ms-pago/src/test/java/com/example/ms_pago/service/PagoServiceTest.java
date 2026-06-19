package com.example.ms_pago.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ms_pago.dto.PagoDTO;
import com.example.ms_pago.model.Pago;
import com.example.ms_pago.repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository repository;

    @InjectMocks
    private PagoService service;

    @Test
    void crearNormalizaEstadoYGuardaPago() {
        when(repository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pago pago = service.crear(dto("pendiente"));

        assertEquals("PENDIENTE", pago.getEstado());
        verify(repository).save(any(Pago.class));
    }

    @Test
    void aprobarCambiaEstado() {
        Pago pago = new Pago(1L, 1L, "TARJETA", BigDecimal.valueOf(10000), "PENDIENTE");
        when(repository.findById(1L)).thenReturn(Optional.of(pago));
        when(repository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pago aprobado = service.aprobar(1L);

        assertEquals("APROBADO", aprobado.getEstado());
    }

    @Test
    void obtenerLanzaExcepcionSiNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L));
    }

    private PagoDTO dto(String estado) {
        PagoDTO dto = new PagoDTO();
        dto.setPedidoId(1L);
        dto.setMetodoPago("TARJETA");
        dto.setMonto(BigDecimal.valueOf(10000));
        dto.setEstado(estado);
        return dto;
    }
}
