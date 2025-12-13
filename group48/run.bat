:: type "./run.bat" in the terminal in this directory to run the program (Works for Windows)

@echo off
echo Compiling...

javac -d bin src/main/*.java

echo Running...
java -cp bin main.Main
