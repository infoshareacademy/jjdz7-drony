#!/bin/bash

line=$(head -n 1 .env)
arr=(${line//=/ })
password=${arr[1]}
file_path=./App/src/main/resources/META-INF/persistence.xml
orginal=$(sed '16q;d' $file_path)
new="            <property name=\"javax.persistence.jdbc.password\" value=\""$password"\"/>"
sed -i "s|$orginal|$new|g" $file_path

echo "===== RUNNING MAVEN ====="
mvn clean install

sed -i "s|$new|$orginal|g" $file_path

echo "=========================="

echo "==== BUILDING IMAGES ===="

docker-compose down
docker-compose build

echo "===== RUNNING CONTAINERS ====="

docker-compose up -d