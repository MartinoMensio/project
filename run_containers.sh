#!/bin/bash

echo "this script runs everything inside containers"

# compile the webservices
pushd backend
mvn install -DskipTests || exit 1
popd
pushd GeoServices
mvn install -DskipTests || exit 1
popd

# run them all
docker-compose up