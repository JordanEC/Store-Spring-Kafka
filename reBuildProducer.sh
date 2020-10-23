#!/bin/sh
echo "****************"
echo "Grandle build..."
echo "****************"
./gradlew producer:clean producer:build -xtest
echo "****************"
echo "Docker build..."
echo "****************"
docker build -t jordanec/store-producer:latest producer/.
echo "****************"
echo "Process done build..."
echo "****************"