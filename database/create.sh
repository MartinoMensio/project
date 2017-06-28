#!/bin/sh

# requisites for GeoServices project
echo "entering GeoServices_database folder"
pushd GeoServices_database
source create.sh
popd
echo "GeoServices_database done"
# requisites for backend project
echo "entering backend_database folder"
pushd backend_database
source create.sh
popd
echo "backend_database done"