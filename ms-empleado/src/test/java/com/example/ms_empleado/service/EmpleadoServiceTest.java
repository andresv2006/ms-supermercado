package com.example.ms_empleado.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_empleado.dto.EmpleadoDTO;
import com.example.ms_empleado.model.Empleado;
import com.example.ms_empleado.repository.EmpleadoRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository repo;

    @InjectMocks
    private EmpleadoService service;

    @Test
    void crearCuandoRutYCorreoSonUnicosDebeGuardarEmpleado() {
        EmpleadoDTO dto = dto();
        Empleado guardado = new Empleado(1L, dto.getRut(), dto.getNombre(), dto.getApellido(), dto.getCorreo(),
                dto.getTelefono(), dto.getCargo(), dto.getTurno(), dto.getSueldo(), dto.getActivo());

        when(repo.existsByRut(dto.getRut())).thenReturn(false);
        when(repo.existsByCorreo(dto.getCorreo())).thenReturn(false);
        when(repo.save(any(Empleado.class))).thenReturn(guardado);

        Empleado response = service.crear(dto);

        assertEquals(1L, response.getId());
        assertEquals("Cajero", response.getCargo());
        verify(repo).save(any(Empleado.class));
    }

    @Test
    void crearCuandoRutExisteDebeLanzarExcepcion() {
        EmpleadoDTO dto = dto();
        when(repo.existsByRut(dto.getRut())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.crear(dto));
        verify(repo, never()).save(any(Empleado.class));
    }

    @Test
    void obtenerCuandoNoExisteDebeLanzarExcepcion() {
        when(repo.findById(20L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(20L));
    }

    private EmpleadoDTO dto() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setRut("11111111-1");
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setCorreo("juan@super.cl");
        dto.setTelefono("999999999");
        dto.setCargo("Cajero");
        dto.setTurno("Manana");
        dto.setSueldo(BigDecimal.valueOf(500000));
        dto.setActivo(true);
        return dto;
    }
}
