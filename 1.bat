@echo off
chcp 65001 > nul
setlocal

cd /d "%~dp0"

echo =========================================
echo CREAR IMAGENES - MS SUPERMERCADO
echo =========================================
echo.
echo Carpeta actual:
cd
echo.
pause

set "JAVA_HOME=D:\java 17"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo.
echo Verificando Java...
echo JAVA_HOME=%JAVA_HOME%
java -version

if errorlevel 1 (
    echo.
    echo ERROR: Java no funciona con esta ruta:
    echo %JAVA_HOME%
    echo.
    echo Revisa que exista:
    echo D:\java 17\bin\java.exe
    echo.
    pause
    exit /b 1
)

echo.
echo Verificando Docker...
docker ps

if errorlevel 1 (
    echo.
    echo ERROR: Docker Desktop no esta listo.
    echo Abre Docker Desktop y espera que diga Engine running.
    echo.
    pause
    exit /b 1
)

echo.
echo =========================================
echo Generando JAR de microservicios
echo =========================================

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
echo Bajando contenedores anteriores
echo =========================================
docker compose down

echo.
echo =========================================
echo Construyendo imagenes Docker
echo =========================================
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
echo IMAGENES CREADAS CORRECTAMENTE
echo =========================================
echo.
echo Para levantar todo despues usa:
echo docker compose up -d
echo.

pause