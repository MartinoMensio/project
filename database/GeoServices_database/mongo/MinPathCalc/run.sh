#!/bin/sh

# Run containers
docker start mongodb
docker start postgis

# Compile project
mvn install -DskipTests

# Run the app
java -jar target/MinPathCalc-0.0.1-SNAPSHOT.jar
