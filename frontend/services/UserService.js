var app = angular.module('App');

app.factory('UserService', ['$http', '$q', '$localStorage', function ($http, $q, $localStorage) {

    var endpoint = "http://localhost:8888";

    return {
        getCurrentUser: function () {
            var deferred = $q.defer();
            if (!$localStorage.token) {
                // no token is stored: this means that user is not logged in
                deferred.reject("not logged in");
            } else {
                $http.get(endpoint + '/api/profile').then(function (result) {
                    deferred.resolve(result.data);
                }, function (result) {
                    deferred.reject(result);
                });
            }
            return deferred.promise;
        },
        login: function (user) {
            var deferred = $q.defer();
            // send authentication data
            $http.post(endpoint + '/api/login', user).then(function (result) {
                // post succeeded
                $localStorage.token = result.data.token;

                deferred.resolve("ok");
            }, function (error) {
                // post failed
                deferred.reject(error);
            });
            return deferred.promise;
        },
        logout: function () {
            var deferred = $q.defer();
            $localStorage.token = null;
            deferred.resolve("logged out");
            return deferred.promise;
        }
    }
}]);