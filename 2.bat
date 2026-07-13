@echo off
chcp 65001 > nul
setlocal

cd /d "%~dp0"

set "JAVA_HOME=D:\java 17"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo =========================================
echo PREPARAR IMAGENES - MS SUPERMERCADO
echo =========================================
echo.

echo Verificando Java...
java -version
if errorlevel 1 (
    echo.
    echo ERROR: Java no funciona. Revisa JAVA_HOME:
    echo %JAVA_HOME%
    echo.
    pause
    exit /b 1
)

echo.
echo Verificando Docker...
docker ps > nul 2>&1
if errorlevel 1 (
    echo.
    echo ERROR: Docker Desktop no esta listo.
    echo Abre Docker Desktop y espera que el motor este iniciado.
    echo.
    pause
    exit /b 1
)

echo.
echo =========================================
echo Deteniendo y eliminando contenedores previos
echo =========================================
docker compose down --remove-orphans

echo.
echo =========================================
echo Eliminando imagenes antiguas del proyecto
echo =========================================
docker rmi ms-supermercado-ms-auth -f
docker rmi ms-supermercado-ms-empleado -f
docker rmi ms-supermercado-ms-categoria -f
docker rmi ms-supermercado-ms-cliente -f
docker rmi ms-supermercado-ms-producto -f
docker rmi ms-supermercado-ms-inventario -f
docker rmi ms-supermercado-ms-carrito -f
docker rmi ms-supermercado-ms-pedido -f
docker rmi ms-supermercado-ms-pago -f
docker rmi ms-supermercado-ms-devolucion -f
docker rmi ms-supermercado-ms-eureka -f
docker rmi ms-supermercado-ms-gateway -f

echo.
echo =========================================
echo Limpiando imagenes sin uso
echo =========================================
docker image prune -f

echo.
echo =========================================
echo Generando JAR de cada microservicio
echo =========================================
echo.

set SERVICES=ms-auth ms-empleado ms-categoria ms-cliente ms-producto ms-inventario ms-carrito ms-pedido ms-pago ms-devolucion ms-eureka ms-gateway

for %%S in (%SERVICES%) do (
    echo.
    echo -----------------------------------------
    echo Empaquetando %%S
    echo -----------------------------------------

    cd /d "%~dp0%%S"

    call mvnw.cmd clean package -DskipTests

    if errorlevel 1 (
        echo.
        echo ERROR: Fallo el empaquetado de %%S
        echo Revisa ese microservicio antes de continuar.
        echo.
        pause
        exit /b 1
    )
)

cd /d "%~dp0"

echo.
echo =========================================
echo Construyendo imagenes Docker
echo =========================================
docker compose build

if errorlevel 1 (
    echo.
    echo ERROR: Fallo la construccion de imagenes Docker.
    echo.
    pause
    exit /b 1
)

echo.
echo =========================================
echo LISTO
echo =========================================
echo.
echo Ya quedaron creadas las imagenes.
echo Ahora puedes levantar todo con:
echo.
echo docker compose up -d
echo.
echo Luego prueba:
echo http://localhost:8761
echo http://localhost:8099/swagger-ui.html
echo.
pause