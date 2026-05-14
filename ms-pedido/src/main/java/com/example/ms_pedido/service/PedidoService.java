package com.example.ms_pedido.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_pedido.client.ClienteClient;
import com.example.ms_pedido.client.ProductoClient;
import com.example.ms_pedido.dto.PedidoDTO;
import com.example.ms_pedido.dto.PedidoDetalleDTO;
import com.example.ms_pedido.model.Pedido;
import com.example.ms_pedido.model.PedidoDetalle;
import com.example.ms_pedido.repository.PedidoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository repo;
    private final ClienteClient clienteClient;
    private final ProductoClient productoClient;

    public Pedido crear(PedidoDTO dto, String token) {
        validarCliente(dto.getClienteId(), token);

        Pedido pedido = new Pedido();
        pedido.setClienteId(dto.getClienteId());
        pedido.setEstado(dto.getEstado());
        pedido.setTotal(BigDecimal.ZERO);
        pedido.setDetalles(new ArrayList<>());

        BigDecimal total = BigDecimal.ZERO;

        for (PedidoDetalleDTO detalleDTO : dto.getDetalles()) {
            validarProducto(detalleDTO.getProductoId(), token);

            PedidoDetalle detalle = new PedidoDetalle(
                    null,
                    detalleDTO.getProductoId(),
                    detalleDTO.getCantidad(),
                    detalleDTO.getPrecioUnitario(),
                    pedido
            );

            pedido.getDetalles().add(detalle);
            total = total.add(detalleDTO.getPrecioUnitario().multiply(BigDecimal.valueOf(detalleDTO.getCantidad())));
        }

        pedido.setTotal(total);
        return repo.save(pedido);
    }

    public List<Pedido> listar() {
        return repo.findAll();
    }

    public Pedido obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
    }

    public Pedido actualizarEstado(Long id, String estado) {
        Pedido pedido = obtener(id);
        pedido.setEstado(estado);
        return repo.save(pedido);
    }

    public void eliminar(Long id) {
        repo.delete(obtener(id));
    }

    private void validarCliente(Long clienteId, String token) {
        if (clienteClient.obtener(clienteId, token) == null) {
            throw new IllegalArgumentException("Cliente no existe");
        }
    }

    private void validarProducto(Long productoId, String token) {
        if (productoClient.obtener(productoId, token) == null) {
            throw new IllegalArgumentException("Producto no existe");
        }
    }
}
