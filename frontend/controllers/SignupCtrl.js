var app = angular.module('App');

app.controller('SignupCtrl', ['UserService', '$state', function (UserService, $state) {
    UserService.logout();
    
    this.submit = () => {
        console.log('submit');
        UserService.signup(this.registration).then(function (result) {
            console.log('registred successfully in');
            // TODO go to a page that shows: "an email was sent to ..."
            $state.go('page.home', null, { reload: true });
        })
    }
}]);