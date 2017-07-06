#!/bin/sh

# docker stuff

# cleanup
docker stop mongodb 2>/dev/null
docker rm mongodb 2>/dev/null
docker volume rm ai_project_mongo 2>/dev/null 

# create volume
docker volume create ai_project_mongo || exit 1
# get mongo image
docker pull mongo || exit 1
# create container
docker run --name mongodb -v ai_project_mongo:/data/db -p 27017:27017 -d mongo || exit 1

# and run the project that populates it
pushd MinPathCalc
bash run.sh || exit 1
popd