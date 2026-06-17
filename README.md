# Sistema de Supermercado con Microservicios

Integrantes:

Enzo muñoz
Sebastian sellao 
Andres vargas

## Descripcion

Este proyecto corresponde a un backend distribuido para la gestion de un supermercado. La solucion utiliza una arquitectura de microservicios independientes, donde cada servicio representa un dominio del negocio y mantiene su propia base de datos relacional.

El sistema permite administrar autenticacion, categorias, productos, clientes, inventario, carrito de compras, pedidos, pagos, devoluciones y empleados. Los microservicios aplican el patron Controller-Service-Repository, persistencia con Spring Data JPA, migraciones con Flyway, validaciones, reglas de negocio, manejo centralizado de excepciones, logs estructurados y seguridad con JWT.

## Microservicios

| Microservicio | Responsabilidad | Puerto |
| --- | --- | --- |
| ms-auth | Registro, login y generacion de tokens JWT | 8080 |
| ms-empleado | Gestion de empleados | 8081 |
| ms-categoria | Gestion de categorias | 8082 |
| ms-cliente | Gestion de clientes | 8083 |
| ms-producto | Gestion de productos | 8084 |
| ms-inventario | Gestion de inventario | 8085 |
| ms-carrito | Gestion de carrito de compras | 8086 |
| ms-pedido | Gestion de pedidos | 8087 |
| ms-pago | Gestion de pagos | 8088 |
| ms-devolucion | Gestion de devoluciones | 8089 |
| ms-eureka | Server Discovery | 8761 |
| ms-gateway | API Gateway | 8090 |

## Arquitectura

El cliente consume el sistema por medio del API Gateway. Eureka permite registrar y descubrir servicios usando nombres logicos, evitando depender directamente de puertos fijos dentro de la arquitectura.

Flujo general:

1. El usuario inicia sesion en `ms-auth`.
2. `ms-auth` genera un token JWT.
3. El cliente envia el token en el header `Authorization`.
4. `ms-gateway` enruta las peticiones hacia los microservicios.
5. Cada microservicio protegido valida el JWT antes de ejecutar la operacion.

## Comunicacion REST

Relaciones principales entre servicios:

- `ms-producto` consulta `ms-categoria`.
- `ms-carrito` consulta `ms-cliente` y `ms-producto`.
- `ms-pedido` consulta `ms-cliente` y `ms-producto`.
- `ms-pago` consulta `ms-pedido`.
- `ms-devolucion` consulta `ms-pedido` y `ms-pago`.
- `ms-inventario` consulta `ms-producto`.

## API Gateway

Rutas configuradas actualmente para los servicios trabajados:

| Ruta | Servicio destino |
| --- | --- |
| `/auth/**` | `lb://ms-auth` |
| `/api/categorias/**` | `lb://ms-categoria` |
| `/api/empleados/**` | `lb://ms-empleado` |
| `/api/carritos/**` | `lb://ms-carrito` |

El gateway se ejecuta en el puerto `8090` porque `ms-auth` utiliza el puerto `8080`.

## Eureka

Eureka Server se ejecuta en:

```text
http://localhost:8761
```

Orden recomendado de ejecucion:

1. `ms-eureka`
2. `ms-auth`
3. `ms-categoria`
4. `ms-empleado`
5. `ms-carrito`
6. `ms-gateway`

## Swagger

Swagger/OpenAPI se revisa directamente en cada microservicio:

```text
http://localhost:8080/swagger-ui/index.html
http://localhost:8081/swagger-ui/index.html
http://localhost:8082/swagger-ui/index.html
http://localhost:8086/swagger-ui/index.html
```

## Pruebas

El proyecto incorpora pruebas unitarias con JUnit y Mockito en los servicios principales. Para ejecutar pruebas:

```powershell
.\mvnw.cmd test
```

Para generar reporte de cobertura con JaCoCo:

```powershell
.\mvnw.cmd clean test
```

Reporte:

```text
target/site/jacoco/index.html
```

## Docker

Para construir la arquitectura con Docker se agregaron `Dockerfile` en los servicios trabajados y un archivo `docker-compose.yml` en la raiz.

Primero generar los JAR:

```powershell
.\mvnw.cmd clean package -DskipTests
```

Luego levantar con Docker Compose desde la raiz:

```powershell
docker compose up --build
```

Para detener:

```powershell
docker compose down
```

## Flujo de prueba en Postman

1. Registrar o iniciar sesion en `ms-auth`.
2. Copiar `data.accessToken`.
3. Enviar el token como `Authorization: Bearer TOKEN`.
4. Crear categoria.
5. Crear cliente.
6. Crear producto usando `categoriaId`.
7. Crear carrito usando `clienteId` y `productoId`.
8. Crear pedido usando `clienteId` y `productoId`.
9. Crear pago usando `pedidoId`.
10. Crear devolucion usando `pedidoId` y `pagoId`.

## Requisitos aplicados

- Microservicios independientes.
- Base de datos por servicio.
- Patron Controller-Service-Repository.
- Persistencia con Spring Data JPA.
- Migraciones con Flyway.
- Validaciones y reglas de negocio.
- Seguridad JWT.
- Comunicacion REST entre microservicios.
- Manejo centralizado de excepciones.
- Logs estructurados.
- Swagger/OpenAPI.
- HATEOAS.
- Pruebas unitarias con JUnit y Mockito.
- Cobertura con JaCoCo.
- Eureka Server como Service Discovery.
- API Gateway.
- Docker y Docker Compose.
