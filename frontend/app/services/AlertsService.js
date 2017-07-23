var app = angular.module('App');

app.factory('AlertsService', ['$http', '$q', '__env', function ($http, $q, __env) {

    var endpoint = __env.backend + 'api/';

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
        },
        voteAlert: function (alertId, vote) {
            var deferred = $q.defer();
            $http.put(endpoint + 'alerts/' + alertId + '/rate', { value: vote }).then(function (result) {
                // TODO this is because the trigger on backend is run after returning
                $http.get(endpoint + 'alerts/' + alertId).then(function (result) {
                    deferred.resolve(result.data);
                }, function (result) {
                    deferred.reject(result);
                });
            }, function (result) {
                deferred.reject(result);
            });
            return deferred.promise;
        },
        getUserRatingToAlert: function (id) {
            var deferred = $q.defer();
            $http.get(endpoint + 'alerts/' + id + '/rate').then(function (result) {
                deferred.resolve(result.data);
            }, function (result) {
                deferred.reject(result);
            });
            return deferred.promise;
        },
        findHashtag: function (text) {
            var regexp = /\B\#\w+\b/g
            result = text.match(regexp);
            if (result) {
                // return the first hashtag (skipping the # char)
                return result[0].substring(1);
            } else {
                return null;
            }
        },
        autocompleteHashtag: function (text, hashtag) {
            var regexp = /\B\#\w+\b/g
            result = text.match(regexp);
            if (result) {
                // return the first hashtag (skipping the # char)
                return text.replace(result[0], `#${hashtag}`);
            } else {
                return text;
            }
        }
    }
}]);