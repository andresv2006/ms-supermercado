package com.example.ms_producto.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_producto.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoriaClient {

    private final WebClient webClient;
    private static final String BASE_URL = "http://localhost:8082/api/categorias/";

    public Object obtener(Long id, String token) {
        
        ApiResponse<Object> response = webClient.get()
                .uri(BASE_URL + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<Object>>() {})
                .block();

        return response != null ? response.getData() : null;
    }
}