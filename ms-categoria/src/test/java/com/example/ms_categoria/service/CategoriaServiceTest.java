package com.example.ms_categoria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
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
    void listarDebeRetornarCategorias() {
        when(repo.findAll()).thenReturn(List.of(new Categoria(1L, "Panaderia", "Pan", true)));

        List<Categoria> response = service.listar();

        assertEquals(1, response.size());
        assertEquals("Panaderia", response.get(0).getNombre());
    }

    @Test
    void obtenerCuandoExisteDebeRetornarCategoria() {
        when(repo.findById(1L)).thenReturn(Optional.of(new Categoria(1L, "Bebidas", "Liquidos", true)));

        Categoria response = service.obtener(1L);

        assertEquals("Bebidas", response.getNombre());
    }

    @Test
    void obtenerCuandoNoExisteDebeLanzarExcepcion() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L));
    }

    @Test
    void actualizarCuandoExisteDebeModificarYGuardar() {
        Categoria actual = new Categoria(1L, "Bebidas", "Liquidos", true);
        CategoriaDTO dto = dto("Abarrotes", "Productos secos", false);

        when(repo.findById(1L)).thenReturn(Optional.of(actual));
        when(repo.save(actual)).thenReturn(actual);

        Categoria response = service.actualizar(1L, dto);

        assertEquals("Abarrotes", response.getNombre());
        assertEquals("Productos secos", response.getDescripcion());
        assertEquals(false, response.getActivo());
        verify(repo).save(actual);
    }

    @Test
    void actualizarCuandoNombreEsCortoDebeLanzarExcepcion() {
        CategoriaDTO dto = dto("AB", "Productos", true);

        assertThrows(IllegalArgumentException.class, () -> service.actualizar(1L, dto));
        verify(repo, never()).findById(1L);
    }

    @Test
    void eliminarCuandoExisteDebeEliminarCategoria() {
        Categoria categoria = new Categoria(1L, "Bebidas", "Liquidos", true);
        when(repo.findById(1L)).thenReturn(Optional.of(categoria));

        service.eliminar(1L);

        verify(repo).delete(categoria);
    }

    private CategoriaDTO dto(String nombre, String descripcion, Boolean activo) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre(nombre);
        dto.setDescripcion(descripcion);
        dto.setActivo(activo);
        return dto;
    }
}