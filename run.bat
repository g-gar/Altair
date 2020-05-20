@echo off

cd target
java -cp altair-1.0-SNAPSHOT.jar;dependency/* com.ggar.altair.Application
cd ..

@echo on