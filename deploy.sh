#!/bin/bash

line=$(head -n 1 .env)
arr=(${line//=/ })
password=${arr[1]}
file_path=./Web/src/main/resources/META-INF/persistence.xml
file_path2=./Web/src/main/webapp/WEB-INF/web.xml
orginal=$(sed '16q;d' $file_path)
orginal2=$(sed '10q;d' $file_path2)
new="            <property name=\"javax.persistence.jdbc.password\" value=\""$password"\"/>"
new2="        <password>"$password"</password>"
sed -i "s|$orginal|$new|g" $file_path
sed -i "s|$orginal2|$new2|g" $file_path2

echo "===== RUNNING MAVEN ====="
mvn clean install

#sed -i "s|$new|$orginal|g" $file_path

echo "=========================="

echo "==== BUILDING IMAGES ===="

docker-compose down
docker-compose build

echo "===== RUNNING CONTAINERS ====="

docker-compose up -d