#!/bin/sh

# create the postgis database
echo "entering postgis folder"
pushd postgis
bash postgis_create.sh || exit 1
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
    bash create.sh || exit 1
fi
popd
echo "mongo done"