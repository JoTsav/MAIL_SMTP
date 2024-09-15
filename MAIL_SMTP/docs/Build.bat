REM Joseph Kianda Tshava

@echo off
cls

setlocal enabledelayedexpansion

echo Change JAVA_HOME path

set JAVA_HOME="C:\jdk-21"
set PATH=%JAVA_HOME%\bin;%PATH%
set USE_JAVAFX=true
set JAVAFX_HOME="C:\javafx-sdk-21"
set JAVAFX_MODULES=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media
set JAVAFX_ARGS=
if %USE_JAVAFX%==true (set JAVAFX_ARGS=--module-path %JAVAFX_HOME%\lib --add-modules=%JAVAFX_MODULES%)
echo %USE_JAVAFX%, %JAVAFX_ARGS%

set ERRMSG=

:VERSION
echo ~~~ Checking Version ~~~
javac -version
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG="Error checking version"
    GOTO ERROR
)
java -version
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG="Error checking version"
    GOTO ERROR
)

pause
echo Build script set to run in Project folder
cd ..

set PRAC_BIN=.\bin
set PRAC_DOC=.\docs
set PRAC_JDC=JavaDoc
set PRAC_LIB=.\lib\*
set PRAC_SRC=.\src\main\java\com\mail\mail_smtp

:CLEAN
echo ~~~ Cleaning project ~~~
DEL /S %PRAC_BIN%\*.class
RMDIR /Q /S %PRAC_DOCS%\%PRAC_JDC%
IF /I "%ERRORLEVEL%" NEQ "0" (
    echo ~~! Error cleaning project !~~
)

:COMPILE
echo ~~~ Compiling project ~~~
javac %JAVAFX_ARGS% -sourcepath %PRAC_SRC% -cp %PRAC_BIN%;%PRAC_LIB% -d %PRAC_BIN% %PRAC_SRC%\Main.java
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG=~~! Error compiling project !~~
    GOTO ERROR
)

:JAVADOC
echo ~~~ Generate JavaDoc for project ~~~
javadoc %JAVAFX_ARGS% -sourcepath %PRAC_SRC% -classpath %PRAC_BIN%;%PRAC_LIB% -use -version -author -d %PRAC_DOCS%\%PRAC_JDC% -subpackages acsse
IF /I "%ERRORLEVEL%" NEQ "0" (
    echo ~~! Error generating JavaDoc for project !~~
)

:RUN
echo ~~~ Running project ~~~
java %JAVAFX_ARGS% -cp %PRAC_BIN%;%PRAC_LIB% Main %*
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG=~~! Error running project !~~
    GOTO ERROR
)
GOTO END

:ERROR
echo ~~! Fatal error with project !~~
echo %ERRMSG%

:END
echo ~~~ End ~~~
cd %PRAC_DOCS%
pause
