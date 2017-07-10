#!/bin/sh

# Run containers
docker start mongodb
docker start postgis

# Compile project
mvn clean install -DskipTests

# Run the app
java -jar target/GeoServices-0.0.1-SNAPSHOT.jar
