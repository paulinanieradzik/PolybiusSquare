#!/bin/sh
mvn clean package && docker build -t pl.polsl/PolybiusSquare .
docker rm -f PolybiusSquare || true && docker run -d -p 9080:9080 -p 9443:9443 --name PolybiusSquare pl.polsl/PolybiusSquare