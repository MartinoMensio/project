#!/bin/sh

# create the postgis database
echo "entering postgis folder"
pushd postgis
source postgis_create.sh
popd
echo "postgis done"
# create the mongo database
echo "entering mongo folder"
pushd mongo
echo
echo
read -p "Are you sure you want to recreate mongo data? It will take some hours (y/N) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
    source create.sh
fi
popd
echo "mongo done"