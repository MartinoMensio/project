# project
Final project of the course "Applicazioni Internet"

## Components

- frontend: this is the angular webapp client
- backend: the main REST backend for the application (responsible for users/chat/warnings)
- GeoServices: the secondary web service for geographical stuff

## Instructions

- Create the containers and initialize the databases (follow the scripts in the `database` folder)
- Start the backend: this is a spring boot app
- Start the GeoServices: also this is a spring boot app
- Start the frontend using `npm start` in its folder (after doing `npm install`)

## Self-signed certificates

The browser will reject to connect to the three components by default.

You need to manually go to the following URLs:

- https://localhost:8888/ 
- https://localhost:9999/
- https://localhost:8080/

and explicitly add security exceptions for them.

The frontend application is reachable at https://localhost:8080/
