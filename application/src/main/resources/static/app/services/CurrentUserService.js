var app = angular.module('App');

app.factory('CurrentUserService', ['$http', '$q', function($http, $q) {
	
	return {
        getCurrentUser: function () {
            var deferred = $q.defer();
            $http.get('/api/me').then(function (result) {
                deferred.resolve(result.data);
            }, function (result) {
                deferred.reject(result);
            });
            return deferred.promise;
        }
    }
}]);