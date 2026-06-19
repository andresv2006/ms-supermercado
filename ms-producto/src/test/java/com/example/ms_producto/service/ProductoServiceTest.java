package com.example.ms_producto.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.example.ms_producto.client.CategoriaClient;
import com.example.ms_producto.dto.ProductoDTO;
import com.example.ms_producto.model.Producto;
import com.example.ms_producto.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository repository;

    @Mock
    private CategoriaClient categoriaClient;

    @InjectMocks
    private ProductoService service;

    @Test
    void crearValidaCategoriaYGuardaProducto() {
        when(categoriaClient.obtener(eq(1L), anyString())).thenReturn(new Object());
        when(repository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Producto producto = service.crear(dto(), "Bearer token");

        assertEquals("SKU-1", producto.getSku());
        verify(repository).save(any(Producto.class));
    }

    @Test
    void obtenerLanzaExcepcionSiNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L));
    }

    private ProductoDTO dto() {
        ProductoDTO dto = new ProductoDTO();
        dto.setSku("SKU-1");
        dto.setNombre("Arroz");
        dto.setDescripcion("Bolsa 1kg");
        dto.setPrecio(BigDecimal.valueOf(1500));
        dto.setCategoriaId(1L);
        dto.setActivo(true);
        return dto;
    }
}