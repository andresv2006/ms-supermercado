package com.example.ms_pedido.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotBlank(message = "El estado del pedido es obligatorio y no puede estar en blanco")
    private String estado;

    @Valid
    @NotEmpty(message = "El pedido debe contener al menos un detalle (producto)")
    private List<PedidoDetalleDTO> detalles;
}
