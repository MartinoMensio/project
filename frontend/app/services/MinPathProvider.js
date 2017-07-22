var app = angular.module('App');

app.factory('MinPathProvider', ['DataProvider', 'MongoRestClient', '$q', '$http', '$timeout', '$rootScope', function (DataProvider, MongoRestClient, $q, $http, $timeout, $rootScope) {

    var endpoint = 'http://localhost:9999/'
    var stops = DataProvider.getStops();
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
    var getEdgeFeature = function (edge) {
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
            var srcStop = stops.find(s => s.id === edge.idSource);
            if (srcStop) {
                // add the stop coordinates to the array
                result.data.coordinates.push([srcStop.latLng[1], srcStop.latLng[0]]);
            }
            var dstStop = stops.find(s => s.id === edge.idDestination);
            if (dstStop) {
                // add the stop coordinates to the array
                result.data.coordinates.push([dstStop.latLng[1], dstStop.latLng[0]]);
            }
        } else {
            // this is a bus edge
            edge.stopsId.forEach(function (stopId) {
                var stop = stops.find(s => s.id === stopId);
                if (stop) {
                    // add the stop coordinates to the array
                    result.data.coordinates.push([stop.latLng[1], stop.latLng[0]]);
                }
            }, this);
        }
        return result;
    }

    var getEdgeSourceMarker = function (edge) {
        var srcStop = stops.find(s => s.id === edge.idSource);
        var result = {
            lat: srcStop.latLng[0],
            lng: srcStop.latLng[1],
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

    var createEdge = function (src, dst, msg) {
        return {
            data: {
                type: "LineString",
                coordinates: [[src[1], src[0]], [dst[1], dst[0]]],
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

    // do the conversion from MinPath to geoJson
    var getResultFromMinPath = function (minPath, src, dst) {
        var result = {
            // is filled later
            geojson: [],
            markers: {},
            latlngs: []
        }
        if (minPath) {
            // min path exists
            var firstStop = stops.find(s => s.id === minPath.idSource);
            var lastStop = stops.find(s => s.id === minPath.idDestination);

            // add the first edge
            result.geojson.push(createEdge([src.lat, src.lng], firstStop.latLng, 'walk from the selected location to the stop ' + firstStop.id));
            minPath.edges.forEach(function (edge) {
                var edgeFeature = getEdgeFeature(edge);
                // nested geojson for the edge
                result.geojson.push(edgeFeature);
                // get a marker for the source of the edge
                var edgeSourceMarker = getEdgeSourceMarker(edge);
                result.markers[edge.idSource] = edgeSourceMarker;
            }, this);
            // add the last edge
            result.geojson.push(createEdge(lastStop.latLng, [dst.lat, dst.lng], 'walk from stop ' + lastStop.id + ' to your destination'));
            // add three more markers (one for source, one for destination and one for penultimate)
            // those three markers are not built in the for loop because they are not source of an edge represented in the MinPath
            result.markers['penultimate'] = createMarker(lastStop.latLng, '<h3>' + lastStop.id + ' - ' + lastStop.name + '</h3>by walk');
        } else {
            // minPath does not exist because srcStopId ad dstStopId are the same
            result.geojson.push(createEdge([src.lat, src.lng], [dst.lat, dst.lng], 'walk from the source to the destination'));
        }
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
    }

    // returns the nearest bus stop to the provided point
    var findNearestStop = function (point) {
        var minDistSq = Infinity;
        var bestStop = null;
        stops.forEach(function (stop) {
            var distSq = Math.pow(point.lat - stop.latLng[0], 2) + Math.pow(point.lng - stop.latLng[1], 2);
            if (distSq < minDistSq) {
                bestStop = stop;
                minDistSq = distSq;
            }
        }, this);
        return bestStop;
    }

    // returns an array with bus stops nearest to the provided point
    var findNearestStops = function (point) {
        var deferred = $q.defer();
        $http.get(endpoint + 'findBusStops', { params: { lat: point.lat, lng: point.lng } }).then(function (result) {
            deferred.resolve(result.data);
        }, function (result) {
            deferred.reject(result);
        });
        return deferred.promise;
    }

    function sendError(message) {
        $rootScope.$emit('error', { message: message });
    }

    return {
        // src and dst are objects {lat,lng}
        getMinPathBetween: function (src, dst) {
            var path = null;
            var nearestSrc = findNearestStop(src).id;
            var nearestDst = findNearestStop(dst).id;
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
            }).catch((error) => {
                // notify about the error
                sendError(error);
                return $q.reject(error);
            });

        }
    }
}]);
