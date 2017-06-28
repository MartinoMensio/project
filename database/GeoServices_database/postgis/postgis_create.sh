#!/bin/sh

# cleanup
docker stop postgis 2>/dev/null
docker rm postgis 2>/dev/null
docker rmi ai_project/postgis 2>/dev/null
docker volume rm ai_project_postgis 2>/dev/null 

# create volume
docker volume create ai_project_postgis
# create image
pushd Docker_postgis
docker build -t ai_project/postgis .
popd
# create the container
docker run --name postgis -v ai_project_postgis:/datadb -p 5432:5432 -d ai_project/postgis

# import from json to database
pushd JsonToDB
source run.sh
popd

# from normal table to geographical
pushd PostGisDBCreator
source run.sh
popd