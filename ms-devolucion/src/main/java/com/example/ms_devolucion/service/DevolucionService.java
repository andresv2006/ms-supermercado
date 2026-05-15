package com.example.ms_devolucion.service;

import java.util.List;

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
        return repo.save(new Devolucion(
                null,
                dto.getPedidoId(),
                dto.getPagoId(),
                dto.getMotivo(),
                dto.getEstado()
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
        item.setEstado(dto.getEstado());

        return repo.save(item);
    }

    public void eliminar(Long id) {
        repo.delete(obtener(id));
    }
}
