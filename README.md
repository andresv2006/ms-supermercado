Integrantes: Enzo muñoz
             Sebastian sellao 
             Andres vargas


El proyecto corresponde a un sistema backend distribuido para la gestión de un supermercado desarrollado a base de una arquitectura de microservicios independiendes
estos siendo 10 con que el cada 1 de ellos representa un modelo de negocios para estos y con su propia base de datos. 

La solución está compuesta por servicios para autenticación, categorías, productos, clientes, inventario, carrito de compras, pedidos, pagos, devoluciones y empleados , 
Cada uno implementa el patrón Controller-Service-Repository, persistencia de datos con Spring Data JPA, entidades JPA, DTOs, validaciones, reglas de negocio, 
manejo centralizado de excepciones, logs estructurados y migraciones de base de datos mediante Flyway incluyendo un sistema de seguridad basica mediante jwt y rest 
entre los microservicios permitiendo la validacion de informacion distribuida 


pasos a seguir:

1. Iniciar MySQL desde Laragon
2. Crear las bases de datos necesarias
3. Levantar primero ms-auth
4. Levantar los demás microservicios del proyecto
5. Probar endpoints desde Postman usando token JWT
6.  Iniciar sesión en ms-auth
7. Copiar el token JWT
8. Crear una categoría
9. Crear un cliente
10. Crear un producto usando categoriaId
11. Crear un carrito usando clienteId y productoId
12. Crear un pedido usando clienteId` y productoId
13. Crear un pago usando pedidoId
14. Crear una devolución usando pedidoId` y pagoId
