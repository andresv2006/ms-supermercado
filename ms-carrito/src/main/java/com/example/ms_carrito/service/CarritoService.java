package com.example.ms_carrito.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_carrito.client.ClienteClient;
import com.example.ms_carrito.client.ProductoClient;
import com.example.ms_carrito.dto.CarritoDTO;
import com.example.ms_carrito.dto.CarritoItemDTO;
import com.example.ms_carrito.model.Carrito;
import com.example.ms_carrito.model.CarritoItem;
import com.example.ms_carrito.repository.CarritoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarritoService {

    private final CarritoRepository repo;
    private final ClienteClient clienteClient;
    private final ProductoClient productoClient;

    public Carrito crear(CarritoDTO dto, String token) {
        validarCliente(dto.getClienteId(), token);

        Carrito carrito = new Carrito();
        carrito.setClienteId(dto.getClienteId());
        carrito.setEstado(dto.getEstado());
        carrito.setTotal(BigDecimal.ZERO);
        carrito.setItems(new ArrayList<>());

        BigDecimal total = BigDecimal.ZERO;

        for (CarritoItemDTO itemDTO : dto.getItems()) {
            validarProducto(itemDTO.getProductoId(), token);

            CarritoItem item = new CarritoItem(
                    null,
                    itemDTO.getProductoId(),
                    itemDTO.getCantidad(),
                    itemDTO.getPrecioUnitario(),
                    carrito
            );

            carrito.getItems().add(item);
            total = total.add(itemDTO.getPrecioUnitario().multiply(BigDecimal.valueOf(itemDTO.getCantidad())));
        }

        carrito.setTotal(total);
        return repo.save(carrito);
    }

    public List<Carrito> listar() {
        return repo.findAll();
    }

    public Carrito obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));
    }

    public Carrito actualizar(Long id, CarritoDTO dto, String token) {
        validarCliente(dto.getClienteId(), token);

        Carrito carrito = obtener(id);
        carrito.setClienteId(dto.getClienteId());
        carrito.setEstado(dto.getEstado());
        carrito.getItems().clear();

        BigDecimal total = BigDecimal.ZERO;

        for (CarritoItemDTO itemDTO : dto.getItems()) {
            validarProducto(itemDTO.getProductoId(), token);

            CarritoItem item = new CarritoItem(
                    null,
                    itemDTO.getProductoId(),
                    itemDTO.getCantidad(),
                    itemDTO.getPrecioUnitario(),
                    carrito
            );

            carrito.getItems().add(item);
            total = total.add(itemDTO.getPrecioUnitario().multiply(BigDecimal.valueOf(itemDTO.getCantidad())));
        }

        carrito.setTotal(total);
        return repo.save(carrito);
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
