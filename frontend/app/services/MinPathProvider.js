var app = angular.module('App');

app.factory('MinPathProvider', ['BusLinesService', 'MongoRestClient', '$q', '$http', '$timeout', '$rootScope', function (BusLinesService, MongoRestClient, $q, $http, $timeout, $rootScope) {

    var last_color_modified = 0;

    // returns a RGB color with luminance not greater than 50% and saturation 100%
    var getColor = function (mode) {
        // cycle the variation from 0 to 3
        last_color_modified = (++last_color_modified) % 4;

        if (mode) {
            // walking color (near blue)
            var rgb_base = [0, 50, 200];
        } else {
            // bus color (near orange)
            var rgb_base = [255, 50, 0];
        }
        // change in cycle the green
        rgb_base[1] += 50 * last_color_modified;
        var result = '#';
        for (var index = 0; index < 3; index++) {
            var r_256 = rgb_base[index].toString(16);
            var padded = '00'.substring(r_256.length) + r_256;
            result += padded;
        }
        return result;
    }

    // returns the geojson for an edge
    var getEdgeFeature = function (edge, stops) {
        var msg = edge.mode ? 'walk from stop ' + edge.idSource + ' to stop ' + edge.idDestination : 'take line ' + edge.lineId + ' from stop ' + edge.idSource + ' to stop ' + edge.idDestination;
        var result = {
            data: {
                type: "LineString",
                coordinates: [],
                properties: {
                    name: "line",
                    mode: edge.mode,
                    lineId: edge.lineId,
                    srcId: edge.idSource,
                    dstId: edge.idDestination,
                    msg: msg
                }
            },
            style: {
                color: getColor(edge.mode),
                weight: 5,
                opacity: 1
            }
        }
        if (edge.mode) {
            // this is a walk edge
            var srcStop = stops[edge.idSource];
            if (srcStop) {
                // add the stop coordinates to the array
                result.data.coordinates.push([srcStop.longitude, srcStop.latitude]);
            }
            var dstStop = stops[edge.idDestination];
            if (dstStop) {
                // add the stop coordinates to the array
                result.data.coordinates.push([dstStop.longitude, dstStop.latitude]);
            }
        } else {
            // this is a bus edge
            edge.stopsId.forEach(function (stopId) {
                var stop = stops[stopId];
                if (stop) {
                    // add the stop coordinates to the array
                    result.data.coordinates.push([stop.longitude, stop.latitude]);
                }
            });
        }
        return result;
    }

    var getEdgeSourceMarker = function (edge, stops) {
        var srcStop = stops[edge.idSource];
        var result = {
            lat: srcStop.latitude,
            lng: srcStop.longitude,
            focus: false,
            message: '<h3>' + srcStop.id + ' - ' + srcStop.name + '</h3>'
        }
        if (edge.mode) {
            result.message += 'proceed by walk';
        } else {
            result.message += 'take the line ' + edge.lineId;
        }
        return result;
    };

    var createStraightEdge = function (src, dst, msg) {
        return {
            data: {
                type: "LineString",
                coordinates: [[src.longitude, src.latitude], [dst.longitude, dst.latitude]],
                properties: {
                    name: "line",
                    mode: true,
                    lineId: null,
                    msg: msg
                }
            },
            style: {
                color: getColor(true),
                weight: 5,
                opacity: 1
            }
        };
    }

    var createMarker = function (point, msg) {
        return {
            lat: point[0],
            lng: point[1],
            focus: false,
            message: msg
        };
    }

    var getAllRequiredBusStops = function (minPath) {
        var promises = [];
        var results = {};
        minPath.edges.forEach((edge) => {
            if (edge.mode) {
                // by walk: get the source and destination bus stops
                promises.push(BusLinesService.getBusStopById(edge.idSource).then((res) => {
                    results[edge.idSource] = res;
                }));
                promises.push(BusLinesService.getBusStopById(edge.idDestination).then((res) => {
                    results[edge.idDestination] = res;
                }));
            } else {
                // by bus: the stops can be read all together
                promises.push(BusLinesService.getBusStopsOfLine(edge.lineId).then((res) => {
                    res.forEach((busStop) => {
                        results[busStop.id] = busStop;
                    });
                }));
            }
        });
        return $q.all(promises).then((promiseResults) => {
            return results;
        })
    }

    // do the conversion from MinPath to geoJson
    var getResultFromMinPath = function (minPath, src, dst) {
        var result = {
            // is filled later
            geojson: [],
            markers: {},
            latlngs: []
        }

        // what to wait (contain geojson and markers)
        var promise;

        if (minPath) {
            // min path exists
            promise = getAllRequiredBusStops(minPath).then((busStops) => {
                // build the edges with the required informations
                // busStops is a map id -> busStop
                var edges = [];
                var markers = {};

                var firstStop = busStops[minPath.idSource];
                var lastStop = busStops[minPath.idDestination];
                // add the first edge from the clicked point to the source of MinPath
                edges.push(createStraightEdge({ latitude: src.lat, longitude: src.lng }, firstStop, 'walk from the selected location to the stop ' + firstStop.id));
                minPath.edges.forEach(edge => {
                    // add the edge
                    var edgeFeature = getEdgeFeature(edge, busStops);
                    edges.push(edgeFeature);
                    // and the marker
                    var edgeSourceMarker = getEdgeSourceMarker(edge, busStops);
                    markers[edge.idSource] = edgeSourceMarker;
                });
                // add the last edge
                edges.push(createStraightEdge(lastStop, { latitude: dst.lat, longitude: dst.lng }, 'walk from stop ' + lastStop.id + ' to your destination'));
                // add one more markers for penultimate
                markers['penultimate'] = createMarker([lastStop.latitude, lastStop.longitude], '<h3>' + lastStop.id + ' - ' + lastStop.name + '</h3>by walk');

                return {
                    geojson: edges,
                    markers: markers
                }
            })
        } else {
            // provide a shortcut from src to dst
            edge = createStraightEdge({ latitude: src.lat, longitude: src.lng }, { latitude: dst.lat, longitude: dst.lng }, 'walk from the source to the destination');
            promise = $q.resolve({ geojson: [edge], markers: {} });
        }
        // final part for the final result, adding beginning and end markers and computing the boundary
        return promise.then((subresults) => {
            result.geojson = subresults.geojson;
            result.markers = subresults.markers;
            // always add the marker for the clicked source and the clicked destination
            result.markers['source'] = createMarker([src.lat, src.lng], 'Walk away');
            result.markers['destination'] = createMarker([dst.lat, dst.lng], 'destination reached');
            // fill an array of latlng for centering the map
            result.geojson.forEach(function (feature) {
                feature.data.coordinates.forEach(function (coordinate) {
                    // transform [x,y] to {lat, lng}
                    result.latlngs.push(L.GeoJSON.coordsToLatLng(coordinate));
                });
            });
            return result;
        });
    }

    function sendError(message) {
        $rootScope.$emit('error', { message: message });
    }

    return {
        // src and dst are objects {lat,lng}
        getMinPathBetween: function (src, dst) {
            var path = null;
            return $q.all([BusLinesService.findNearestStop(src), BusLinesService.findNearestStop(dst)]).then((results) => {
                var nearestSrc = results[0];
                var nearestDst = results[1];
                // check if user needs to take some buses
                if (nearestSrc === nearestDst) {
                    // don't go to the nearest stop just to go to the destination, better to go directly to destination since no bus is taken
                    var deferred = $q.defer();
                    deferred.resolve(getResultFromMinPath(null, src, dst));
                    return deferred.promise;
                }
                return MongoRestClient.getMinPathBetweenPositions(src.lat, src.lng, dst.lat, dst.lng).then((minPath) => {
                    // transform MinPath to GeoJson
                    return getResultFromMinPath(minPath, src, dst);
                });
            }).catch((error) => {
                // notify about the error
                sendError(error);
                return $q.reject(error);
            });



        }
    }
}]);
