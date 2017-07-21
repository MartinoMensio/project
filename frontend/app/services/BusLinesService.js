var app = angular.module('App');

app.factory('BusLinesService', ['$http', '$q', 'DataProvider', function ($http, $q, DataProvider) {

    var endpoint = 'http://localhost:9999/';

    return {
        /*
         * Returns the list of bus lines
         * 
        */
        getLines: function () {
            var deferred = $q.defer();

            $http.get(endpoint + 'busLines').then(function (response) {    
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
        getLineByIdAsGeoJson: function (lineId) {
            var deferred = $q.defer();

            $http.get(endpoint + 'busLines/' + lineId).then(function (response) {

                //TODO convert dato to geoJSON format


                deferred.resolve(response.data);
            }, function (error) {
                deferred.reject(error);
            });

            return deferred.promise;
        }
    }
}]);