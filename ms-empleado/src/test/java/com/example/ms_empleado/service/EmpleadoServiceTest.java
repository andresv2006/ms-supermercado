package com.example.ms_empleado.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
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
        Empleado guardado = empleado(1L, dto);

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
    void crearCuandoCorreoExisteDebeLanzarExcepcion() {
        EmpleadoDTO dto = dto();
        when(repo.existsByRut(dto.getRut())).thenReturn(false);
        when(repo.existsByCorreo(dto.getCorreo())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.crear(dto));
        verify(repo, never()).save(any(Empleado.class));
    }

    @Test
    void listarDebeRetornarEmpleados() {
        when(repo.findAll()).thenReturn(List.of(empleado(1L, dto())));

        List<Empleado> response = service.listar();

        assertEquals(1, response.size());
        assertEquals("Cajero", response.get(0).getCargo());
    }

    @Test
    void obtenerCuandoExisteDebeRetornarEmpleado() {
        when(repo.findById(1L)).thenReturn(Optional.of(empleado(1L, dto())));

        Empleado response = service.obtener(1L);

        assertEquals("11111111-1", response.getRut());
    }

    @Test
    void obtenerCuandoNoExisteDebeLanzarExcepcion() {
        when(repo.findById(20L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(20L));
    }

    @Test
    void actualizarCuandoDatosNoDuplicanDebeGuardarCambios() {
        Empleado actual = empleado(1L, dto());
        EmpleadoDTO nuevo = dto();
        nuevo.setNombre("Pedro");
        nuevo.setCargo("Supervisor");

        when(repo.findById(1L)).thenReturn(Optional.of(actual));
        when(repo.save(actual)).thenReturn(actual);

        Empleado response = service.actualizar(1L, nuevo);

        assertEquals("Pedro", response.getNombre());
        assertEquals("Supervisor", response.getCargo());
        verify(repo).save(actual);
    }

    @Test
    void actualizarCuandoRutNuevoExisteDebeLanzarExcepcion() {
        Empleado actual = empleado(1L, dto());
        EmpleadoDTO nuevo = dto();
        nuevo.setRut("22222222-2");

        when(repo.findById(1L)).thenReturn(Optional.of(actual));
        when(repo.existsByRut("22222222-2")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.actualizar(1L, nuevo));
        verify(repo, never()).save(any(Empleado.class));
    }

    @Test
    void actualizarCuandoCorreoNuevoExisteDebeLanzarExcepcion() {
        Empleado actual = empleado(1L, dto());
        EmpleadoDTO nuevo = dto();
        nuevo.setCorreo("otro@super.cl");

        when(repo.findById(1L)).thenReturn(Optional.of(actual));
        when(repo.existsByCorreo("otro@super.cl")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.actualizar(1L, nuevo));
        verify(repo, never()).save(any(Empleado.class));
    }

    @Test
    void eliminarCuandoExisteDebeEliminarEmpleado() {
        Empleado empleado = empleado(1L, dto());
        when(repo.findById(1L)).thenReturn(Optional.of(empleado));

        service.eliminar(1L);

        verify(repo).delete(empleado);
    }

    private Empleado empleado(Long id, EmpleadoDTO dto) {
        return new Empleado(id, dto.getRut(), dto.getNombre(), dto.getApellido(), dto.getCorreo(),
                dto.getTelefono(), dto.getCargo(), dto.getTurno(), dto.getSueldo(), dto.getActivo());
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