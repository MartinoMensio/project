var app = angular.module('App');

app.factory('UserService', ['$http', '$q', '$localStorage', '$rootScope', function ($http, $q, $localStorage, $rootScope) {

    var endpoint = "http://localhost:8888";

    return {
        endpoint: endpoint,
        getCurrentUser: function () {
            var deferred = $q.defer();
            if (!$localStorage.token) {
                // no token is stored: this means that user is not logged in
                deferred.reject("not logged in");
            } else {
                $http.get(endpoint + '/api/profile').then(function (result) {
                    $rootScope.user = result.data;
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
        signup: function(registration) {
            var deferred = $q.defer();
            // send registration data
            $http.post(endpoint + '/api/signup', registration).then(function (result) {
                // post succeeded
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
            $rootScope.user = null;
            deferred.resolve("logged out");
            return deferred.promise;
        },
        changePassword: function (form) {
            var deferred = $q.defer();
            // send password change data
            $http.put(endpoint + '/api/profile/password', form).then(function (result) {
                // post succeeded
                deferred.resolve("ok");
            }, function (error) {
                // post failed
                deferred.reject(error);
            });
            return deferred.promise;
        },
        updateProfile: function (profile) {
            var deferred = $q.defer();
            // send profile data
            $http.put(endpoint + '/api/profile', profile).then(function (result) {
                // post succeeded
                deferred.resolve(result.data);
            }, function (error) {
                // post failed
                deferred.reject(error);
            });
            return deferred.promise;
        },
        
        // functions for the profile attributes

        getCarSharingServices: function () {
            var deferred = $q.defer();
            $http.get(endpoint + '/api/attributes/carSharingServices').then(function (result) {
                // post succeeded
                deferred.resolve(result.data);
            }, function (error) {
                // post failed
                deferred.reject(error);
            });
            return deferred.promise;
        },
        getEducationLevels: function () {
            var deferred = $q.defer();
            $http.get(endpoint + '/api/attributes/educationLevels').then(function (result) {
                // post succeeded
                deferred.resolve(result.data);
            }, function (error) {
                // post failed
                deferred.reject(error);
            });
            return deferred.promise;
        },
        getEmployments: function () {
            var deferred = $q.defer();
            $http.get(endpoint + '/api/attributes/employments').then(function (result) {
                // post succeeded
                deferred.resolve(result.data);
            }, function (error) {
                // post failed
                deferred.reject(error);
            });
            return deferred.promise;
        },
        getFuels: function () {
            var deferred = $q.defer();
            $http.get(endpoint + '/api/attributes/fuels').then(function (result) {
                // post succeeded
                deferred.resolve(result.data);
            }, function (error) {
                // post failed
                deferred.reject(error);
            });
            return deferred.promise;
        },
        travelDocuments: function () {
            var deferred = $q.defer();
            $http.get(endpoint + '/api/attributes/travelDocuments').then(function (result) {
                // post succeeded
                deferred.resolve(result.data);
            }, function (error) {
                // post failed
                deferred.reject(error);
            });
            return deferred.promise;
        }
    }
}]);