var app = angular.module('App');

app.factory('AuthenticationInterceptor', ['$q', '$state', '$localStorage', function ($q, $state, $localStorage) {
    return {
        'request': function (config) {
            config.headers = config.headers || {};
            if ($localStorage.token) {
                config.headers.Authorization = "Bearer " + $localStorage.token;
            }
            return config;
        },
        'responseError': function (response) {
            if (response.status === 401 || response.status === 403) {
                $state.go('page.login');
            }
            return $q.reject(response);
        }
    };
}])