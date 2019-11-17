#!/bin/sh

echo "========================   RUNNING MAVEN   ========================"
mvn clean install
echo "==================================================================="

echo "========================== BUILDING IMAGES ========================"

docker-compose down

docker-compose build
docker-compose up -d

