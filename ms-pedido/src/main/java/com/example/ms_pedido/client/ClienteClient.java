package com.example.ms_pedido.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.ms_pedido.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClienteClient {

    private final WebClient webClient;
    private static final String BASE_URL = "http://localhost:8083/api/clientes/";

    public Object obtener(Long id, String token) {
        try {
            ApiResponse<Object> response = webClient.get()
                    .uri(BASE_URL + id)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<Object>>() {})
                    .block();

            return response != null ? response.getData() : null;

        } catch (WebClientResponseException e) {
            System.err.println("Error al obtener cliente HTTP " + e.getStatusCode());
            return null;
        } catch (Exception e) {
            System.err.println("Error de conexion con el ms-cliente: " + e.getMessage());
            return null;
        }
    }
}
