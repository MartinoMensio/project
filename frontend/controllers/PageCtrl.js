var app = angular.module('App');

app.controller('PageCtrl', ['UserService', '$state', 'user', function (UserService, $state, user) {
	
	this.user = user;
	this.logout = () => {
		UserService.logout().then(() => {
			$state.go('page.home', null, { reload: true });
		});
	};
}]);