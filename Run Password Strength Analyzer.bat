@echo off
setlocal
cd /d "%~dp0"

java -version >nul 2>&1
if errorlevel 1 (
    echo Java was not found. Install Java 25 or later, then run this file again.
    pause
    exit /b 1
)

java -jar "%~dp0PasswordStrengthAnalyzer.jar"
if errorlevel 1 (
    echo The application could not be started. Make sure Java 25 or later is installed.
    pause
    exit /b 1
)
