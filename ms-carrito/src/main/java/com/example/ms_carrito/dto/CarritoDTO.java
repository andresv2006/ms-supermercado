package com.example.ms_carrito.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarritoDTO {

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @Valid
    @NotEmpty(message = "El carrito debe tener al menos un producto")
    private List<CarritoItemDTO> items;
}
