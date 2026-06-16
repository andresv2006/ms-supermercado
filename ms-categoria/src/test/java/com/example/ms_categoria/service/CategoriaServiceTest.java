package com.example.ms_categoria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_categoria.dto.CategoriaDTO;
import com.example.ms_categoria.model.Categoria;
import com.example.ms_categoria.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repo;

    @InjectMocks
    private CategoriaService service;

    @Test
    void crearCuandoDatosValidosDebeGuardarCategoria() {
        CategoriaDTO dto = dto("Lacteos", "Productos refrigerados", true);
        Categoria guardada = new Categoria(1L, "Lacteos", "Productos refrigerados", true);

        when(repo.save(any(Categoria.class))).thenReturn(guardada);

        Categoria response = service.crear(dto);

        assertEquals(1L, response.getId());
        assertEquals("Lacteos", response.getNombre());
        verify(repo).save(any(Categoria.class));
    }

    @Test
    void crearCuandoNombreEsCortoDebeLanzarExcepcion() {
        CategoriaDTO dto = dto("AB", "Descripcion", true);

        assertThrows(IllegalArgumentException.class, () -> service.crear(dto));
        verify(repo, never()).save(any(Categoria.class));
    }

    @Test
    void obtenerCuandoNoExisteDebeLanzarExcepcion() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L));
    }

    private CategoriaDTO dto(String nombre, String descripcion, Boolean activo) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre(nombre);
        dto.setDescripcion(descripcion);
        dto.setActivo(activo);
        return dto;
    }
}
