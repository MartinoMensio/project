var app = angular.module('App');

app.factory('MongoRestClient', ['$http', '$q', '__env', function ($http, $q, __env) {

    var endpoint = __env.geoservices;

    return {
        getMinPath: function (srcId, dstId) {
            var deferred = $q.defer();
            $http.get(endpoint + 'min_paths/' + srcId + '_' + dstId).then(function (result) {
                deferred.resolve(result.data);
            }, function (result) {
                deferred.reject(result);
            });
            return deferred.promise;
        },
        getMinPathBetweenPositions: function (srcLat, srcLng, dstLat, dstLng) {
            var deferred = $q.defer();
            $http.get(endpoint + 'getMinPathBetweenPositions', {params: {srcLat: srcLat, srcLng: srcLng, dstLat: dstLat, dstLng: dstLng}}).then(function (result) {
                if (!result.data) {
                    deferred.reject('Minimum path not found between the selected positions');
                } else {
                    deferred.resolve(result.data);
                }
            }, function (result) {
                deferred.reject(result);
            });
            return deferred.promise;
        }
    }
}]);