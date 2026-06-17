package com.example.ms_producto.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_producto.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoriaClient {

    private final WebClient webClient;

    @Value("${categoria.service.url}")
    private String baseUrl;

    public Object obtener(Long id, String token) {
        ApiResponse<Object> response = webClient.get()
                .uri(baseUrl + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<Object>>() {})
                .block();

        return response != null ? response.getData() : null;
    }
}
