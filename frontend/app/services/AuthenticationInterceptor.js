var app = angular.module('App');

app.factory('AuthenticationInterceptor', ['$q', '$state', '$localStorage', '$injector', '$rootScope', function ($q, $state, $localStorage, $injector, $rootScope) {
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
            $rootScope.$emit('error', response.data || {message: "Communication error while contacting " + response.config.url});
            if (response.status === 401 || response.status === 403) {
                // save the wanted state
                $rootScope.wantedState = $state.current.name;
                $state.go('page.login');
            }
            return $q.reject(response);
        }
    };
}])