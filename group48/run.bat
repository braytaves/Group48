@echo off
echo Compiling...

javac -d bin src/main/*.java

echo Running...
java -cp bin main.UnchangedLinesTestClass
