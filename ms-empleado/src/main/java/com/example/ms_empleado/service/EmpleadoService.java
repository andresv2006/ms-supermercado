package com.example.ms_empleado.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_empleado.dto.EmpleadoDTO;
import com.example.ms_empleado.model.Empleado;
import com.example.ms_empleado.repository.EmpleadoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpleadoService {

    private final EmpleadoRepository repo;

    public Empleado crear(EmpleadoDTO dto) {
        log.info("Crear empleado", keyValue("rut", dto.getRut()), keyValue("cargo", dto.getCargo()));

        validarRutCorreoUnicos(dto.getRut(), dto.getCorreo());

        Empleado empleado = new Empleado(
                null,
                dto.getRut(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getCorreo(),
                dto.getTelefono(),
                dto.getCargo(),
                dto.getTurno(),
                dto.getSueldo(),
                dto.getActivo()
        );

        return repo.save(empleado);
    }

    public List<Empleado> listar() {
        log.info("Listar empleados");
        return repo.findAll();
    }

    public Empleado obtener(Long id) {
        log.info("Obtener empleado", keyValue("id", id));

        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
    }

    public Empleado actualizar(Long id, EmpleadoDTO dto) {
        log.info("Actualizar empleado", keyValue("id", id));

        Empleado empleado = obtener(id);

        if (!empleado.getRut().equals(dto.getRut()) && repo.existsByRut(dto.getRut())) {
            throw new RuntimeException("Ya existe un empleado con ese rut");
        }

        if (!empleado.getCorreo().equals(dto.getCorreo()) && repo.existsByCorreo(dto.getCorreo())) {
            throw new RuntimeException("Ya existe un empleado con ese correo");
        }

        empleado.setRut(dto.getRut());
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setCorreo(dto.getCorreo());
        empleado.setTelefono(dto.getTelefono());
        empleado.setCargo(dto.getCargo());
        empleado.setTurno(dto.getTurno());
        empleado.setSueldo(dto.getSueldo());
        empleado.setActivo(dto.getActivo());

        return repo.save(empleado);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar empleado", keyValue("id", id));

        Empleado empleado = obtener(id);
        repo.delete(empleado);
    }

    private void validarRutCorreoUnicos(String rut, String correo) {
        if (repo.existsByRut(rut)) {
            throw new RuntimeException("Ya existe un empleado con ese rut");
        }

        if (repo.existsByCorreo(correo)) {
            throw new RuntimeException("Ya existe un empleado con ese correo");
        }
    }
}
