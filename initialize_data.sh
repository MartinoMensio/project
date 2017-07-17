#!/bin/bash

echo "this script sets up the databases for docker-compose"

# compile the jars for creating the data
pushd database/GeoServices_database/postgis/JsonToDB/
mvn clean install
popd
pushd database/GeoServices_database/postgis/PostGisDBCreator/
mvn clean install
popd
pushd database/GeoServices_database/mongo/MinPathCalc/
mvn clean install
popd

# create the postgres database for the backend
WHAT=backend_db docker-compose -f docker-compose.init.yml -f docker-compose.yml up --abort-on-container-exit
# create the postgis database for GeoServices
WHAT=postgis docker-compose -f docker-compose.init.yml -f docker-compose.yml up --abort-on-container-exit

# ask if want to recreate MinPaths
echo
read -p "READ ME: Are you sure you want to recreate mongo data? It will take some hours (y/N) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
    WHAT=mongo docker-compose -f docker-compose.init.yml -f docker-compose.yml up --abort-on-container-exit
fi

echo
echo "databases have been initialized"