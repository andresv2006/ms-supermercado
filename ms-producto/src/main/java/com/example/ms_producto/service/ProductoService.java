package com.example.ms_producto.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.ms_producto.dto.ApiResponse;
import com.example.ms_producto.dto.ProductoDTO;
import com.example.ms_producto.model.Producto;
import com.example.ms_producto.repository.ProductoRepository;
import com.example.ms_producto.client.CategoriaClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoService {

    private final ProductoRepository repo;
    private final CategoriaClient categoriaClient;

    public Producto crear(ProductoDTO dto, String token) {
        validar(dto, token);
        
        return repo.save(new Producto(
                null, 
                dto.getSku(), 
                dto.getNombre(), 
                dto.getDescripcion(), 
                dto.getPrecio(), 
                dto.getCategoriaId(), 
                dto.getActivo()
        ));
    }

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto obtener(Long id) {
        return repo.findById(id).orElseThrow(() -> 
                new EntityNotFoundException("Producto no encontrado")
        );
    }

    public Producto actualizar(Long id, ProductoDTO dto, String token) {
        validar(dto, token);
        
        Producto item = obtener(id);
        item.setSku(dto.getSku());
        item.setNombre(dto.getNombre());
        item.setDescripcion(dto.getDescripcion());
        item.setPrecio(dto.getPrecio());
        item.setCategoriaId(dto.getCategoriaId());
        item.setActivo(dto.getActivo());
        
        return repo.save(item);
    }

    public void eliminar(Long id) {
        repo.delete(obtener(id));
    }

    private void validar(ProductoDTO dto, String token) {
        if (categoriaClient.obtener(dto.getCategoriaId(), token) == null) {
            throw new IllegalArgumentException("Categoria no existe");
        }
    }
}
