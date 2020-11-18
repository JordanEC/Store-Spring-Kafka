#!/bin/sh
echo "****************"
echo "producer: gradle build..."
echo "****************"
./gradlew producer:clean producer:build -xtest
echo "****************"
echo "producer: docker build..."
echo "****************"
docker build -t jordanec/store-producer:latest producer/.
echo "****************"
echo "producer: build done!"
echo "****************"
echo "****************"
echo "consumer: gradle build..."
echo "****************"
./gradlew producer:clean producer:build -xtest
echo "****************"
echo "consumer: docker build..."
echo "****************"
docker build -t jordanec/store-consumer:latest consumer/.
echo "****************"
echo "consumer: build done!"
echo "****************"