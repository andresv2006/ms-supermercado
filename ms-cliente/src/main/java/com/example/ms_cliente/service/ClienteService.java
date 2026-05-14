package com.example.ms_cliente.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_cliente.dto.ClienteDTO;
import com.example.ms_cliente.model.Cliente;
import com.example.ms_cliente.repository.ClienteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {
    private final ClienteRepository repo;

    public Cliente crear(ClienteDTO dto) {
        log.info("Crear cliente");
        validarReglas(dto);
        Cliente item = new Cliente(null, dto.getRut(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getCorreo(),
                dto.getTelefono(),
                dto.getDireccion(),
                dto.getActivo());
        return repo.save(item);
    }

    public List<Cliente> listar() {
        log.info("Listar clientes");
        return repo.findAll();
    }

    public Cliente obtener(Long id) {
        log.info("Obtener cliente", keyValue("id", id));
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
    }

    public Cliente actualizar(Long id, ClienteDTO dto) {
        log.info("Actualizar cliente", keyValue("id", id));
        validarReglas(dto);
        Cliente item = obtener(id);
        item.setRut(dto.getRut());
        item.setNombre(dto.getNombre());
        item.setApellido(dto.getApellido());
        item.setCorreo(dto.getCorreo());
        item.setTelefono(dto.getTelefono());
        item.setDireccion(dto.getDireccion());
        item.setActivo(dto.getActivo());
        return repo.save(item);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar cliente", keyValue("id", id));
        repo.delete(obtener(id));
    }

    private void validarReglas(ClienteDTO dto) {
    }
}
