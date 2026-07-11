@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion

set "PROJECT_DIR=%~dp0"
cd /d "%PROJECT_DIR%"

if exist "D:\java 17\bin\java.exe" (
    set "JAVA_HOME=D:\java 17"
    set "PATH=%JAVA_HOME%\bin;%PATH%"
)

echo =========================================
echo Empaquetando microservicios sin tests
echo =========================================

set SERVICES=ms-auth ms-categoria ms-empleado ms-carrito ms-cliente ms-producto ms-inventario ms-pedido ms-pago ms-devolucion ms-eureka ms-gateway

for %%S in (%SERVICES%) do (
    echo.
    echo -----------------------------------------
    echo Compilando %%S
    echo -----------------------------------------
    cd /d "%PROJECT_DIR%%%S"
    call mvnw.cmd clean package -DskipTests
    if errorlevel 1 (
        echo.
        echo Error compilando %%S. Se detiene el proceso.
        pause
        exit /b 1
    )
)

cd /d "%PROJECT_DIR%"

echo.
echo =========================================
echo Levantando Docker Compose
echo =========================================
docker compose up --build

pause
