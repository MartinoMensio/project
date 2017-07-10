# project
Final project of the course "Applicazioni Internet"

## Components

- frontend: this is the angular webapp client
- backend: the main REST backend for the application (responsible for users/chat/warnings)

## Instructions

- Start the backend and GeoServices: those are spring boot apps that can be built and run using their `run.sh` script file
- Start the frontend using `live-server` in its folder

## Instruction for the docker-compose strategy

### Initialization of data

```bash

# compile the projects that will put the data into dbs
# those commands create the jars that will be executed by the initialize container
pushd database/GeoServices_database/postgis/JsonToDB/
mvn clean install
popd
pushd database/GeoServices_database/postgis/PostGisDBCreator/
mvn clean install
popd
pushd database/GeoServices_database/mongo/MinPathCalc/
mvn clean install
popd

# now run the following command to execute the initialization of each database
WHAT=postgis docker-compose -f docker-compose.init.yml -f docker-compose.yml up
WHAT=backend_db docker-compose -f docker-compose.init.yml -f docker-compose.yml up 
WHAT=mongo docker-compose -f docker-compose.init.yml -f docker-compose.yml up

```
You are now ready to run the webservices

### Running webservices

#### Inside docker

To compile the jars for the backend and the frontend run the followiing:
```bash
pushd backend
mvn clean install -DskipTests
popd
pushd GeoServices
mvn clean install -DskipTests
popd
```

In the repository root, simply run the command `docker-compose up`.

This will run docker-compose using the files `docker-compose.yml` and `docker-compose.override.yml`.

All the components (excluding frontend) will run inside a container

#### Outside docker (STS or CLI) for dev phase

For running the projects outside the container while the databases run inside containers, use the following command to run the databases.

```bash
docker-compose -f docker-compose.yml up
# in this way the docker-compose.override.yml will not be used
```

After that you can run webservices in your IDE/CLI that will connect to containers.