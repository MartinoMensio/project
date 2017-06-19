var app = angular.module('App');

app.factory('GeocodingService', ['$http', '$q', 'DataProvider', function ($http, $q, DataProvider) {

    var endpoint = 'http://localhost:9999/geocode';

    return {
        // look at res.geometry.location for latlng
        getLocationFromString: function (queryString) {
            var deferred = $q.defer();
            $http.get(endpoint, {
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
        }
    }
}]);