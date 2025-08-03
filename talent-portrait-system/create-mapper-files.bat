@echo off
echo Creating Mapper interface files...

:: Create mapper directories
mkdir talent-system\src\main\java\com\cgn\talent\system\mapper 2>nul
mkdir talent-core\src\main\java\com\cgn\talent\core\mapper 2>nul

:: Create mapper XML directories
mkdir talent-system\src\main\resources\mapper 2>nul
mkdir talent-core\src\main\resources\mapper 2>nul

echo.
echo Mapper directories created successfully!
echo.
echo Please copy the Java files to:
echo - talent-system\src\main\java\com\cgn\talent\system\mapper\
echo - talent-core\src\main\java\com\cgn\talent\core\mapper\
echo.
echo XML files will be created in:
echo - talent-system\src\main\resources\mapper\
echo - talent-core\src\main\resources\mapper\
echo.
pause