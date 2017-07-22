var app = angular.module('App');

app.controller('ChangePasswordCtrl', ['UserService', '$state', function (UserService, $state) {
    
    this.submit = () => {
        console.log('submit');
        UserService.changePassword(this.form).then(function (result) {
            console.log('changed password');
            $state.go('page.profile', null);
        })
    }
}]);