WHAT=$1
sleep 10

echo $WHAT
if [ -z "$1" ] ; then
    echo "Nothing chosen. Terminating "
    exit 1
fi
if [ "$WHAT" == "postgis" ] ; then
    cd /app/postgis
    java -jar JsonToDB.jar
    java -jar PostGisDBCreator.jar
elif [ "$WHAT" == "backend_db" ] ; then
    cd /app/backend_db
    bash create_compose.sh
elif [ "$WHAT" == "mongo" ] ; then
    cd /app/mongo
    java -jar MinPathCalc.jar
fi

echo "done"
exit 0