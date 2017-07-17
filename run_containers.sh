#!/bin/bash

echo "this script runs everything inside containers"

# compile the webservices
pushd backend
mvn clean install -DskipTests
popd
pushd GeoServices
mvn clean install -DskipTests
popd

# run them all
docker-compose up