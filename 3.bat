@echo off
chcp 65001 > nul
setlocal

cd /d "%~dp0"

set "JAVA_HOME=D:\java 17"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo =========================================
echo CREANDO IMAGENES DE TODOS LOS MS
echo =========================================
echo.

echo Verificando Java...
java -version
if errorlevel 1 (
    echo.
    echo ERROR: Java no funciona con JAVA_HOME=%JAVA_HOME%
    echo.
    pause
    exit /b 1
)

echo.
echo Verificando Docker...
docker ps > nul 2>&1
if errorlevel 1 (
    echo.
    echo ERROR: Docker Desktop no esta iniciado.
    echo Abre Docker Desktop y espera que diga Engine running.
    echo.
    pause
    exit /b 1
)

echo.
echo =========================================
echo Generando JAR con skipTests
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
        echo.
        pause
        exit /b 1
    )
)

cd /d "%~dp0"

echo.
echo =========================================
echo Creando imagenes con Docker Compose
echo =========================================
echo.

docker compose build

if errorlevel 1 (
    echo.
    echo ERROR: Fallo docker compose build.
    echo.
    pause
    exit /b 1
)

echo.
echo =========================================
echo LISTO: IMAGENES CREADAS
echo =========================================
echo.
echo Para levantar los microservicios ejecuta:
echo docker compose up -d
echo.

pause