#!/bin/sh

echo "===== RUNNING MAVEN ====="
mvn clean install

echo "=========================="

echo "==== BUILDING IMAGES ===="

docker-compose down
docker-compose build

echo "===== RUNNING CONTAINERS ====="

docker-compose up -d