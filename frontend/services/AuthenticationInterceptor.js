var app = angular.module('App');

app.factory('AuthenticationInterceptor', ['$q', '$state', '$localStorage', '$injector', function ($q, $state, $localStorage, $injector) {
    return {
        'request': function (config) {
            // manually inject the service to avoid circular dependency
            var UserService = $injector.get('UserService');
            // this interceptor only adds authorization header for calls to the user service enpoint
            if (config.url.startsWith(UserService.endpoint)) {
                config.headers = config.headers || {};
                if ($localStorage.token) {
                    config.headers.Authorization = "Bearer " + $localStorage.token;
                }
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