package com.example.ms_inventario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.repository.InventarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository repository;

    @InjectMocks
    private InventarioService service;

    @Test
    void crearGuardaInventarioSiProductoNoTieneStock() {
        when(repository.findByProductoId(1L)).thenReturn(Optional.empty());
        when(repository.save(any(Inventario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Inventario inventario = service.crear(dto());

        assertEquals(1L, inventario.getProductoId());
        verify(repository).save(any(Inventario.class));
    }

    @Test
    void obtenerLanzaExcepcionSiNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L));
    }

    private InventarioDTO dto() {
        InventarioDTO dto = new InventarioDTO();
        dto.setProductoId(1L);
        dto.setCantidad(20);
        dto.setStockMinimo(5);
        dto.setUbicacion("Bodega A");
        return dto;
    }
}
