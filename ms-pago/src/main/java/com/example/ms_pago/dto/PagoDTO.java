package com.example.ms_pago.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoDTO {

    @NotNull(message = "El pedido es obligatorio")
    private Long pedidoId;

    @NotBlank(message = "El metodo de pago es obligatorio")
    private String metodoPago;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.1", message = "El monto debe ser mayor a cero")
    private BigDecimal monto;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}
