#!/bin/sh

# Run containers
docker start postgis

# Compile project
mvn install -DskipTests

# Run the app
java -jar target/JsonToDB-0.0.1-SNAPSHOT.jar || exit 1
