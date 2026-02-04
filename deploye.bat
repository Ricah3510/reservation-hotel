@echo off
echo ================================
echo  DEPLOIEMENT RESERVATION HOTEL
echo ================================

REM === CONFIG ===
set PROJECT_DIR=D:\S5\Mr_Naina\reservation-hotel
set WAR_NAME=reservation-hotel-1.0-SNAPSHOT.war
set TARGET_DIR=%PROJECT_DIR%\target
set TOMCAT_WEBAPPS=C:\xampp\tomcat\webapps
set TOMCAT_BIN=C:\xampp\tomcat\bin

REM === ETAPE 1 : COMPILATION MAVEN ===
echo.
echo [1/4] Compilation du projet Maven...
cd /d %PROJECT_DIR%
call mvn clean package

if %ERRORLEVEL% neq 0 (
    echo ERREUR : echec de la compilation Maven.
    pause
    exit /b
)

REM === ETAPE 2 : ARRET TOMCAT ===
echo.
echo [2/4] Arret de Tomcat...
call "%TOMCAT_BIN%\shutdown.bat"
timeout /t 5 >nul

REM === ETAPE 3 : DEPLOIEMENT WAR ===
echo.
echo [3/4] Copie du WAR vers Tomcat webapps...

if exist "%TOMCAT_WEBAPPS%\reservation-hotel" (
    rmdir /s /q "%TOMCAT_WEBAPPS%\reservation-hotel"
)

copy "%TARGET_DIR%\%WAR_NAME%" "%TOMCAT_WEBAPPS%\" /Y

REM === ETAPE 4 : DEMARRAGE TOMCAT ===
echo.
echo [4/4] Demarrage de Tomcat...
call "%TOMCAT_BIN%\startup.bat"

echo.
echo ================================
echo  DEPLOIEMENT TERMINE AVEC SUCCES
echo ================================
echo.
echo URL : http://localhost:8080/reservation-hotel
pause
