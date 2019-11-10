#!/bin/bash

mvn clear install
docker-compose down
docker-compose build
docker-compose up -f log
sudo chown patryk:patryk mysql/