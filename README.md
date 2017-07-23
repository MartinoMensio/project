# project
Final project of the course "Applicazioni Internet"

## Components

- frontend: this is the angular webapp client
- backend: the main REST backend for the application (responsible for users/chat/warnings)
- GeoServices: the secondary web service for geographical stuff

## Requirements

- docker-compose
- maven

## Instruction for the docker-compose strategy

### Initialization of data

Execute the script `initialize_data.sh` and confirm to recreate the mongo data.

You are now ready to run the webservices

### Running webservices

#### Inside docker

Execute the script `run_containers.sh`

This will compile the webservices and run docker-compose using the files `docker-compose.yml` and `docker-compose.override.yml`.

All the components (databases, webservices and frontend) will run inside a container

#### Outside docker (STS or CLI) for dev phase of backend

For running the webservices and frontend outside the container while the databases run inside containers, use the script `run_dev.sh`.

After that you can run webservices in your IDE/CLI that will connect to containers.

`backend` and `GeoServices` can be run using their `run.sh` script files, while the frontend can be run using `live-server`.

## Self-signed certificates

The browser will reject to connect to the three components by default.

You need to manually go to the following URLs:

- https://localhost:8888/ 
- https://localhost:9999/
- https://localhost:8080/

and explicitly add security exceptions for them.

The frontend application is reachable at https://localhost:8080/
