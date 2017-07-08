var app = angular.module('App');

app.controller('PageCtrl', ['UserService', '$state', 'user', '$rootScope', function (UserService, $state, user, $rootScope) {
	
	// the error event is emitted when some error are found and need to be displayed
	$rootScope.$on('error', (event, error) => {
		console.log(error);
		this.message = {
			type: "error",
			class: "alert-danger",
			value: error.message
		};
	});

	// this is a friendly message to be shown
	$rootScope.$on('success', (event, message) => {
		console.log(message);
		this.message = {
			type: "message",
			class: "alert-success",
			value: message
		};
	})

	this.user = user;
	this.logout = () => {
		UserService.logout().then(() => {
			$state.go('page.home', null, { reload: true });
		});
	};

	this.hideMessage = () => {
		this.message = null;
	}
}]);