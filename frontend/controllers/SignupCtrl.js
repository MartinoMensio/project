var app = angular.module('App');

app.controller('SignupCtrl', ['UserService', '$state', function (UserService, $state) {
    UserService.logout();
    
    this.submit = () => {
        console.log('submit');
        // set the url to which the user will be sent an email (with params added by the service)
        this.registration.url = $state.href('page.confirmEmail', {}, {absolute: true});
        UserService.signup(this.registration).then((result)=> {
            console.log('registred successfully in');
            // go to a page that shows: "an email was sent to ..."
            $state.go('page.registrationSuccess', {nickname: this.registration.nickname});
        })
    }
}]);