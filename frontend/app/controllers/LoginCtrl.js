var app = angular.module('App');

app.controller('LoginCtrl', ['UserService', '$state', '$rootScope', function (UserService, $state, $rootScope) {
    UserService.logout();
    
    this.submit = () => {
        console.log('submit');
        UserService.login(this.user).then(function (result) {
            console.log('logged in');
            if ($rootScope.wantedState) {
                // resume where user was stopped
                $state.go($rootScope.wantedState, null, {reload: true});
                $rootScope.wantedState = null;
            } else {
                $state.go('page.home', null, { reload: true });
            }
        })
    }
}]);