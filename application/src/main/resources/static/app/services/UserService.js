var app = angular.module('App');

app.factory('UserService', ['$http', '$q', function ($http, $q) {

    getCsrf = function () {
        var deferred = $q.defer();
        $http.get('/rest/csrf').then(function (result) {
            deferred.resolve(result.data);
        }, function (result) {
            deferred.reject(result);
        });
        return deferred.promise;
    }

    return {
        getCurrentUser: function () {
            var deferred = $q.defer();
            $http.get('/api/me').then(function (result) {
                if (result.data === "") {
                    // API called successfully but no user authenticated
                    deferred.reject({reason: "not authenticated"});
                }
                deferred.resolve(result.data);
            }, function (result) {
                deferred.reject(result);
            });
            return deferred.promise;
        },
        getCsrf: getCsrf,
        login: function (user) {
            var deferred = $q.defer();
            getCsrf().then(function (csrf) {
                // after getting csrf
                var myHeader = {};
                myHeader[csrf.headerName] = csrf.token;
                // send authentication data
                $http.post('/api/login', user, {
                    headers: myHeader
                }).then(function (result) {
                    // post succeeded
                    deferred.resolve(result.data);
                }, function (error) {
                    // post failed
                    deferred.reject(error);
                });
            }, function (error) {
                // if csrf fails
                deferred.reject(error);
            });
            return deferred.promise;
        },
        logout: function () {
            var deferred = $q.defer();
            getCsrf().then(function (csrf) {
                // after getting csrf
                var myHeader = {};
                myHeader[csrf.headerName] = csrf.token;
                // send authentication data
                $http.post('/api/logout', {}, {
                    headers: myHeader
                }).then(function (result) {
                    // post succeeded
                    deferred.resolve(result.data);
                }, function (error) {
                    // post failed
                    deferred.reject(error);
                });
            }, function (error) {
                // if csrf fails
                deferred.reject(error);
            });
            return deferred.promise;
        }
    }
}]);