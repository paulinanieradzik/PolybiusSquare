@echo off
call mvn clean package
call docker build -t pl.polsl/PolybiusSquare .
call docker rm -f PolybiusSquare
call docker run -d -p 9080:9080 -p 9443:9443 --name PolybiusSquare pl.polsl/PolybiusSquare