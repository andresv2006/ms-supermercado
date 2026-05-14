package com.example.ms_inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.repository.InventarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository repo;

    public Inventario crear(InventarioDTO dto) {
        return repo.save(new Inventario(
                null,
                dto.getProductoId(),
                dto.getCantidad(),
                dto.getStockMinimo(),
                dto.getUbicacion()
        ));
    }

    public List<Inventario> listar() {
        return repo.findAll();
    }

    public Inventario obtener(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Inventario no encontrado")
        );
    }
}
