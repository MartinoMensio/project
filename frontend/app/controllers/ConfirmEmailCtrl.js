var app = angular.module('App');

app.controller('ConfirmEmailCtrl', ['$stateParams', 'UserService', function($stateParams, UserService) {
    this.email = $stateParams.email;
    this.token = $stateParams.token;
    this.showLogin = false;
    this.status = 'Your email address is being verified';

    UserService.confirmEmail(this.email, this.token).then((result) => {
        this.verificationOK = true;
    }, (error) => {
        this.verificationOK = false;
    })
}]);