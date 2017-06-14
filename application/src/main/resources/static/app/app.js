var app = angular.module('App', ['ngRoute', 'ngResource', 'ui-leaflet', 'ui.router'])

app.config(['$locationProvider', '$urlRouterProvider', '$stateProvider', function ($locationProvider, $urlRouterProvider, $stateProvider) {
    // If the URL does not correspond to anything then redirect to '/'
    $urlRouterProvider.otherwise('/');

    // States defintion
    $stateProvider
        // main abstract state for global things (page template + header)
        .state('page', {
            templateUrl : 'templates/page.html',
            controller: 'PageCtrl',
            controllerAs: 'global',
            abstract: true,
            // those object/promises will be provided to the controllers
            resolve: {
                // get the current user
                user: ['UserService', function(UserService) {
                    return UserService.getCurrentUser().catch(function(error) {
                        return null;
                    })
                }]
            }
        })
        // Home page state
        .state('page.home', {
            url: '/',
            templateUrl: 'templates/home.html'
        })
		// About page state
        .state('page.about', {
            url: '/about',
            templateUrl: 'templates/about.html'
        })
		// Contact page state
		.state('page.contact', {
			url: '/contact',
			templateUrl: 'templates/contact.html'
		})
        // Bus lines list page
        .state('page.busLines', {
            url: '/busLines',
            templateUrl: 'templates/busLinesList.html',
            controller: 'MainCtrl',
            controllerAs: 'ctrl'
        })
        // Bus lines list page + map visualization
        .state('page.busLines.line', {
            url: '/{lineId}',
            templateUrl: 'templates/busLineMap.html',
            controller: 'MainCtrl',
            controllerAs: 'ctrl'
        })
        // Best path page
        .state('page.bestPath', {
            url: '/bestPath',
            templateUrl: 'templates/bestpath.html',
            controller: 'BestPathCtrl',
            controllerAs: 'ctrl'
        })
        // login page
        .state('page.login', {
            url: '/login',
            templateUrl: 'templates/login.html',
            controller: 'LoginCtrl',
            controllerAs: 'ctrl'
        })

    // configure html5 to get links working on jsfiddle
    //$locationProvider.html5Mode(true);
}]);

// TODO vedere se serve definire direttive custom o se ce la caviamo con quelle esistenti
app.directive('myDirective', function () {
    /*
  return {
  };
  */
});
