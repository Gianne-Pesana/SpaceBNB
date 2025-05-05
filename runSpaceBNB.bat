@echo off
cls
chcp 65001 >nul

javac -encoding UTF-8 -d out src\main\java\org\Group5\*.java || exit /b
java -Dfile.encoding=UTF-8 -cp out org.Group5.Main
pause >nul
