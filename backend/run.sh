#!/bin/sh

# Run containers
docker start postgis

# Compile project
mvn clean install -DskipTests

# Run the app
java -jar target/backend-0.0.1-SNAPSHOT.jar
