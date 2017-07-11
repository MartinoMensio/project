var app = angular.module('App');

app.factory('AlertsService', ['$http', '$q', function ($http, $q) {

    var endpoint = 'http://localhost:8888/api/';

    return {
        getAlertTypes: function () {
            var deferred = $q.defer();
            $http.get(endpoint + 'alerts/types').then(function (result) {
                deferred.resolve(result.data);
            }, function (result) {
                deferred.reject(result);
            });
            return deferred.promise;
        },
        getAlerts: function () {
            var deferred = $q.defer();
            $http.get(endpoint + 'alerts').then(function (result) {
                deferred.resolve(result.data);
            }, function (result) {
                deferred.reject(result);
            });
            return deferred.promise;
        },
        getAlertsWithHashtag: function (hashtag) {
            var deferred = $q.defer();
            $http.get(endpoint + 'alerts', { params: { hashtag: hashtag } }).then(function (result) {
                deferred.resolve(result.data);
            }, function (result) {
                deferred.reject(result);
            });
            return deferred.promise;
        },
        getAlertById: function (id) {
            var deferred = $q.defer();
            $http.get(endpoint + 'alerts/' + id).then(function (result) {
                deferred.resolve(result.data);
            }, function (result) {
                deferred.reject(result);
            });
            return deferred.promise;
        },
        saveNewAlert: function (alert) {
            var deferred = $q.defer();
            $http.post(endpoint + 'alerts', alert).then(function (result) {
                deferred.resolve(result.data);
            }, function (result) {
                deferred.reject(result);
            });
            return deferred.promise;
        }
    }
}]);