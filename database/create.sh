#!/bin/sh

# requisites for GeoServices project
echo "entering GeoServices_database folder"
pushd GeoServices_database
bash create.sh || exit 1
popd
echo "GeoServices_database done"
# requisites for backend project
echo "entering backend_database folder"
pushd backend_database 
bash create.sh || exit 1
popd
echo "backend_database done"

echo "all the scripts have terminated correctly"
