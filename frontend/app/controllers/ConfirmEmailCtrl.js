var app = angular.module('App');

app.controller('ConfirmEmailCtrl', ['$stateParams', 'UserService', function($stateParams, UserService) {
    this.email = $stateParams.email;
    this.token = $stateParams.token;
    this.showLogin = false;
    this.status = 'Your email address is being verified';

    UserService.confirmEmail(this.email, this.token).then((result) => {
        this.status = 'Verified. You can now log in!';
        this.showLogin = true;
    }, (error) => {
        this.status = 'Verification failed';
    })
}]);