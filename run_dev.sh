#!/bin/bash

echo "this script runs only the database level inside containers. You can run the webservices and frontend on your own"

# in this way the docker-compose.override.yml will not be used
docker-compose -f docker-compose.yml up
