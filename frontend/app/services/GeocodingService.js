var app = angular.module('App');

app.factory('GeocodingService', ['$http', '$q', function ($http, $q) {

    var endpoint = 'http://localhost:9999/';

    return {
        // look at res.geometry.location for latlng
        getLocationFromString: function (queryString) {
            var deferred = $q.defer();
            $http.get(endpoint + 'geocode', {
                params: {
                    address: queryString
                }
            }).then(function (response) {
                if (!response.data.results.length) {
                    deferred.reject('no geocoding results');
                }
                // take the first result
                deferred.resolve(response.data.results[0]);
            }, function (response) {
                deferred.reject(response);
            });
            return deferred.promise;
        },
        reverseGeocode: function (lat, lng) {
            var deferred = $q.defer();
            $http.get(endpoint + 'reverseGeocode', {
                params: {
                    latlng: lat + ',' + lng
                }
            }).then(function (response) {
                if (!response.data.results.length) {
                    deferred.reject('no geocoding results');
                }
                // take the first result
                deferred.resolve(response.data.results[0]);
            }, function (response) {
                deferred.reject(response);
            });
            return deferred.promise;
        }
    }
}]);