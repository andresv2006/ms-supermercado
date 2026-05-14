package com.example.ms_carrito.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.ms_carrito.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClienteClient {

    private final WebClient webClient;

    @Value("${cliente.service.url}")
    private String baseUrl;

    public Object obtener(Long id, String token) {
        try {
            ApiResponse<Object> response = webClient.get()
                    .uri(baseUrl + id)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<Object>>() {})
                    .block();

            return response != null ? response.getData() : null;
        } catch (WebClientResponseException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
