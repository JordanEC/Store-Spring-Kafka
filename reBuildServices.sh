#!/bin/sh
echo "****************"
echo "producer: gradle build..."
echo "****************"
./gradlew producer:clean producer:build -xtest
echo "****************"
echo "producer: docker build..."
echo "****************"
docker build -t jordanec/store-producer:latest -f producer/Dockerfile ./producer/
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
docker build -t jordanec/store-consumer:latest -f consumer/Dockerfile ./consumer/
echo "****************"
echo "consumer: build done!"
echo "****************"
echo "****************"
echo "store-web: docker build..."
echo "****************"
docker build -t jordanec/store-web:latest -f store-web/Dockerfile-dev ./store-web/
echo "****************"
echo "store-web: build done!"
echo "****************"
echo "****************"
echo "store-web (Prod): docker build..."
echo "****************"
docker build -t jordanec/store-web-prod:latest -f store-web/Dockerfile ./store-web/
#docker build -f store-web/Dockerfile -t jordanec/store-web-prod:latest store-web/.
echo "****************"
echo "store-web (Prod): build done!"
echo "****************"