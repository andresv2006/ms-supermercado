package com.example.ms_producto.controller;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Producto>> crear(
            @Valid @RequestBody ProductoDTO dto,
            @RequestHeader("Authorization") String token) {
        
        return ResponseEntity.status(201).body(
                ApiResponse.<Producto>builder()
                        .success(true)
                        .message("Producto creado")
                        .data(service.crear(dto, token))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Producto>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Producto>>builder()
                        .success(true)
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Producto>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Producto>builder()
                        .success(true)
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Producto>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO dto,
            @RequestHeader("Authorization") String token) {
        
        return ResponseEntity.ok(
                ApiResponse.<Producto>builder()
                        .success(true)
                        .data(service.actualizar(id, dto, token))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Producto eliminado")
                        .build()
        );
    }
}
