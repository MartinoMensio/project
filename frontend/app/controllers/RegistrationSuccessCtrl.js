var app = angular.module('App');

app.controller('RegistrationSuccessCtrl', ['$stateParams', function($stateParams) {
    this.nickname = $stateParams.nickname;
}]);