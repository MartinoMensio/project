var app = angular.module('App');

app.factory('BusLinesService', ['$http', '$q', function ($http, $q) {

    var endpoint = 'http://localhost:9999/';

    // this function returns the points [[x,y]] from a line
    var getLinePoints = function (busStops) {
        var result = [];
        busStops.forEach(function (stop) {
            // add the stop coordinates to the result array
            result.push([stop.longitude, stop.latitude]);
        }, this);
        return result;
    }

    // this function returns the markers of the busstops belonging to a line
    var getStopsMarkers = function (busStops) {
        markers = {};

        busStops.forEach(function (stop) {

            markers[stop.id] = {
                lat: stop.latitude,
                lng: stop.longitude,
                focus: false,
                message: '<h3>' + stop.id + ' - ' + stop.name + '</h3>',
                // link other informations (used later when marker clicked)
                busStop: stop
            }
        }, this);

        return markers;
    }

    return {
        /*
         * Returns the list of bus lines that match the query
         * 
        */
        getLinesByIdContaining: function (query) {
            var deferred = $q.defer();

            $http.get(endpoint + 'busLines/search/getByLine', { params: { line: query } }).then(function (response) {
                deferred.resolve(response.data._embedded.busLines);
            }, function (error) {
                deferred.reject(error);
            });

            return deferred.promise;
        },

        /**
         * Return the BusStops belonging to a line identified by its id
         */
        getBusStopsOfLine: function (lineId) {
            var deferred = $q.defer();

            $http.get(endpoint + 'busLines/' + lineId + '/stops').then(function (response) {
                deferred.resolve(response.data._embedded.busStops);
            }, function (error) {
                deferred.reject(error);
            });

            return deferred.promise;
        },

        /**
         * Return the BusLines passing in the specified busStop (busStop is an object with HATEOAS links)
         */
        getBusLinesPassingAtBusStop: function (busStop) {
            var deferred = $q.defer();

            $http.get(busStop._links.lines.href).then(function (response) {
                deferred.resolve(response.data._embedded.busLines);
            }, function (error) {
                deferred.reject(error);
            });

            return deferred.promise;
        },

        // returns the nearest bus stop to the provided point
        findNearestStop: function (point) {
            var deferred = $q.defer();
            $http.get(endpoint + 'findNearestBusStop', { params: { lat: point.lat, lng: point.lng } }).then(function (result) {
                if (!result.data) {
                    deferred.reject('no bus stops near the selected position');
                } else {
                    deferred.resolve(result.data);
                }
            }, function (result) {
                deferred.reject(result);
            });
            return deferred.promise;
        },
        
        getBusStopById: function(stopId) {
            var deferred = $q.defer();
            $http.get(endpoint + 'busStops/' + stopId).then(function (response) {
                deferred.resolve(response.data);
            }, function (error) {
                deferred.reject(error);
            });
            return deferred.promise;
        },

        /*
         * Returns the bus line associated to the lineId
         * 
        */
        getGeoJsonOfBusStops: function (busStops) {

            var linePoints = getLinePoints(busStops);

            var latlngs = [];
            linePoints.forEach(function (coordinate) {
                // transform [x,y] to {lat, lng}
                latlngs.push(L.GeoJSON.coordsToLatLng(coordinate));
            }, this);

            return {
                // the path of the busline
                geojson: {
                    data: {
                        type: "LineString", coordinates: linePoints
                    },
                    style: {
                        "color": "#ff7800",
                        "weight": 5,
                        "opacity": 0.65
                    }
                },
                // the markers corresponding to the busstops of the line
                markers: getStopsMarkers(busStops),
                // an array of {lat,lng} for centering the map on the provided data
                latlngs: latlngs
            }
        }
    }
}]);