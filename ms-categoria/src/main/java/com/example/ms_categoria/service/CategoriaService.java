package com.example.ms_categoria.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_categoria.dto.CategoriaDTO;
import com.example.ms_categoria.model.Categoria;
import com.example.ms_categoria.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaService {
    private final CategoriaRepository repo;

    public Categoria crear(CategoriaDTO dto) {
        log.info("Crear categoria");
        validarReglas(dto);
        Categoria item = new Categoria(null, dto.getNombre(),
                dto.getDescripcion(),
                dto.getActivo());
        return repo.save(item);
    }

    public List<Categoria> listar() {
        log.info("Listar categorias");
        return repo.findAll();
    }

    public Categoria obtener(Long id) {
        log.info("Obtener categoria", keyValue("id", id));
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria no encontrado"));
    }

    public Categoria actualizar(Long id, CategoriaDTO dto) {
        log.info("Actualizar categoria", keyValue("id", id));
        validarReglas(dto);
        Categoria item = obtener(id);
        item.setNombre(dto.getNombre());
        item.setDescripcion(dto.getDescripcion());
        item.setActivo(dto.getActivo());
        return repo.save(item);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar categoria", keyValue("id", id));
        repo.delete(obtener(id));
    }

    private void validarReglas(CategoriaDTO dto) {
        // Espacio para reglas de negocio del servicio. 
    }
}
