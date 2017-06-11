var app = angular.module('App');

app.controller('GlobalController', ['CurrentUserService', function(CurrentUserService) {
	CurrentUserService.getCurrentUser().then(result => {
		this.user = result;
	}, error => {
		this.user = null;
	});
}]);