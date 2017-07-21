var app = angular.module('App');

app.controller('LoginCtrl', ['UserService', '$state', function (UserService, $state) {
    UserService.logout();
    
    this.submit = () => {
        console.log('submit');
        UserService.login(this.user).then(function (result) {
            console.log('logged in');
            $state.go('page.home', null, { reload: true });
        })
    }
}]);