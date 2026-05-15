package com.example.ms_devolucion.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.example.ms_devolucion.dto.DevolucionDTO;
import com.example.ms_devolucion.model.Devolucion;
import com.example.ms_devolucion.repository.DevolucionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DevolucionService {

    private final DevolucionRepository repo;

    public Devolucion crear(DevolucionDTO dto) {
        String estado = normalizarEstado(dto.getEstado());

        return repo.save(new Devolucion(
                null,
                dto.getPedidoId(),
                dto.getPagoId(),
                dto.getMotivo(),
                estado
        ));
    }

    public List<Devolucion> listar() {
        return repo.findAll();
    }

    public Devolucion obtener(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Devolucion no encontrada")
        );
    }

    public List<Devolucion> listarPorPedido(Long pedidoId) {
        return repo.findByPedidoId(pedidoId);
    }

    public List<Devolucion> listarPorPago(Long pagoId) {
        return repo.findByPagoId(pagoId);
    }

    public Devolucion actualizar(Long id, DevolucionDTO dto) {
        Devolucion item = obtener(id);
        item.setPedidoId(dto.getPedidoId());
        item.setPagoId(dto.getPagoId());
        item.setMotivo(dto.getMotivo());
        item.setEstado(normalizarEstado(dto.getEstado()));

        return repo.save(item);
    }

    public Devolucion aprobar(Long id) {
        Devolucion item = obtener(id);
        item.setEstado("APROBADA");
        return repo.save(item);
    }

    public Devolucion rechazar(Long id) {
        Devolucion item = obtener(id);
        item.setEstado("RECHAZADA");
        return repo.save(item);
    }

    public void eliminar(Long id) {
        repo.delete(obtener(id));
    }

    private String normalizarEstado(String estado) {
        String estadoNormalizado = estado.trim().toUpperCase(Locale.ROOT);
        if (!estadoNormalizado.equals("SOLICITADA")
                && !estadoNormalizado.equals("APROBADA")
                && !estadoNormalizado.equals("RECHAZADA")) {
            throw new IllegalArgumentException("Estado de devolucion no permitido");
        }

        return estadoNormalizado;
    }
}
