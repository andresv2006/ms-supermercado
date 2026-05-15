package com.example.ms_pago.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.example.ms_pago.dto.PagoDTO;
import com.example.ms_pago.model.Pago;
import com.example.ms_pago.repository.PagoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository repo;

    public Pago crear(PagoDTO dto) {
        String estado = normalizarEstado(dto.getEstado());

        return repo.save(new Pago(
                null,
                dto.getPedidoId(),
                dto.getMetodoPago(),
                dto.getMonto(),
                estado
        ));
    }

    public List<Pago> listar() {
        return repo.findAll();
    }

    public Pago obtener(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Pago no encontrado")
        );
    }

    public List<Pago> listarPorPedido(Long pedidoId) {
        return repo.findByPedidoId(pedidoId);
    }

    public Pago actualizar(Long id, PagoDTO dto) {
        Pago item = obtener(id);
        item.setPedidoId(dto.getPedidoId());
        item.setMetodoPago(dto.getMetodoPago());
        item.setMonto(dto.getMonto());
        item.setEstado(normalizarEstado(dto.getEstado()));

        return repo.save(item);
    }

    public Pago aprobar(Long id) {
        Pago item = obtener(id);
        item.setEstado("APROBADO");
        return repo.save(item);
    }

    public Pago rechazar(Long id) {
        Pago item = obtener(id);
        item.setEstado("RECHAZADO");
        return repo.save(item);
    }

    public void eliminar(Long id) {
        repo.delete(obtener(id));
    }

    private String normalizarEstado(String estado) {
        String estadoNormalizado = estado.trim().toUpperCase(Locale.ROOT);
        if (!estadoNormalizado.equals("PENDIENTE")
                && !estadoNormalizado.equals("APROBADO")
                && !estadoNormalizado.equals("RECHAZADO")) {
            throw new IllegalArgumentException("Estado de pago no permitido");
        }

        return estadoNormalizado;
    }
}
