package com.example.ms_categoria.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.ms_categoria.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(ApiResponse.builder().success(false).message("Validacion fallida").error(errores).build());
    }

    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handle404(Exception ex) {
        return ResponseEntity.status(404).body(ApiResponse.builder().success(false).message(ex.getMessage()).build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusiness(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.builder().success(false).message(ex.getMessage()).build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(400).body(ApiResponse.builder().success(false).message(ex.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneral(Exception ex) {
        return ResponseEntity.status(500).body(ApiResponse.builder().success(false).message(ex.getMessage()).build());
    }
}
