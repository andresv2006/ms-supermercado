@echo off
chcp 65001 > nul
setlocal

cd /d "%~dp0"

echo =========================================
echo LIMPIEZA COMPLETA DEL PROYECTO
echo =========================================
echo.

echo Bajando contenedores anteriores...
docker compose down

echo.
echo Eliminando imagenes antiguas del proyecto...
docker rmi ms-supermercado-ms-auth
docker rmi ms-supermercado-ms-empleado
docker rmi ms-supermercado-ms-categoria
docker rmi ms-supermercado-ms-cliente
docker rmi ms-supermercado-ms-producto
docker rmi ms-supermercado-ms-inventario
docker rmi ms-supermercado-ms-carrito
docker rmi ms-supermercado-ms-pedido
docker rmi ms-supermercado-ms-pago
docker rmi ms-supermercado-ms-devolucion
docker rmi ms-supermercado-ms-eureka
docker rmi ms-supermercado-ms-gateway

echo.
echo Limpiando imagenes colgantes...
docker image prune -f

echo.
echo =========================================
echo CONSTRUYENDO Y LEVANTANDO TODO
echo =========================================
echo.

docker compose up -d --build

if errorlevel 1 (
    echo.
    echo ERROR: No se pudieron levantar los microservicios.
    echo Revisa si Docker Desktop esta iniciado correctamente.
    pause
    exit /b 1
)

echo.
echo =========================================
echo LISTO
echo =========================================
echo.
echo Espera 2 o 3 minutos antes de probar.
echo.
echo Eureka:
echo http://localhost:8761
echo.
echo Auth Swagger:
echo http://localhost:8099/swagger-ui.html
echo.
echo Postman:
echo POST http://localhost:8099/auth/register
echo.

pause