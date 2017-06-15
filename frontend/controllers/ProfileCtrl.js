var app = angular.module('App');

app.controller('ProfileCtrl', ['userDetails', function(userDetails) {
    this.userDetails = userDetails;
}]);