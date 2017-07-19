var app = angular.module('App', ['ngRoute', 
                                'ngResource', 
                                'ui-leaflet', 
                                'ui.router', 
                                'ngStorage',
                                'ngStomp',
                                'luegg.directives',
                                'ui.bootstrap',
                                'ui.router.state.events',
                                'angular.img',
                                'angular-input-stars'])

app.config(['$locationProvider', '$urlRouterProvider', '$stateProvider', '$httpProvider', function ($locationProvider, $urlRouterProvider, $stateProvider, $httpProvider) {
    // If the URL does not correspond to anything then redirect to '/'
    $urlRouterProvider.otherwise('/');

    // TODO this interceptor is only for interaction with a specific server
    $httpProvider.interceptors.push('AuthenticationInterceptor');

    // States defintion
    $stateProvider
        // main abstract state for global things (page template + header)
        .state('page', {
            templateUrl: 'templates/page.html',
            controller: 'PageCtrl',
            controllerAs: 'global',
            abstract: true,
            // those object/promises will be provided to the controllers
            resolve: {
                // get the current user
                user: ['UserService', function (UserService) {
                    return UserService.getCurrentUser().catch(function (error) {
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
            controller: 'BusLinesCtrl',
            controllerAs: 'ctrl'
        })
        // Bus lines list page + map visualization
        .state('page.busLines.line', {
            url: '/{lineId}',
            templateUrl: 'templates/busLineMap.html',
            controller: 'BusLinesCtrl',
            controllerAs: 'ctrl'
        })
        // Best path page
        .state('page.bestPath', {
            url: '/bestPath',
            templateUrl: 'templates/bestpath.html',
            controller: 'BestPathCtrl',
            controllerAs: 'ctrl'
        })
        // Chats page
        .state('page.chatTopics', {
            url: '/chatTopics',
            templateUrl: 'templates/chatTopics.html',
            controller: 'ChatTopicsCtrl',
            controllerAs: 'ctrl',
            // these objects/promises will be provided to the controllers
            resolve: {
                // get chat topics
                topics: ['ChatService', function (ChatService) {
                    return ChatService.getTopics();
                }]
            }
        })
        // Chat page
        .state('page.chat', {
            url: '/chat/{topicId}',
            templateUrl: 'templates/chat.html',
            controller: 'ChatCtrl',
            controllerAs: 'ctrl'
        })
        // Alert page
        .state('page.alerts', {
            url: '/alert',
            templateUrl: 'templates/alert.html',
            controller: 'AlertsCtrl',
            controllerAs: 'ctrl'
        })
        // login page
        .state('page.login', {
            url: '/login',
            templateUrl: 'templates/login.html',
            controller: 'LoginCtrl',
            controllerAs: 'ctrl'
        })
        // signup page
        .state('page.signup', {
            url: '/signup',
            templateUrl: 'templates/signup.html',
            controller: 'SignupCtrl',
            controllerAs: 'ctrl'
        })
        // account created
        .state('page.registrationSuccess', {
            url: '/registrationSuccess?nickname',
            templateUrl: 'templates/registrationSuccess.html',
            controller: 'RegistrationSuccessCtrl',
            controllerAs: 'ctrl',
        })
        // confirmation of email
        .state('page.confirmEmail', {
            url: '/confirmEmail?email&token',
            templateUrl: 'templates/confirmEmail.html',
            controller: 'ConfirmEmailCtrl',
            controllerAs: 'ctrl'
        })
        // profile
        .state('page.profile', {
            url: '/profile',
            templateUrl: 'templates/profile.html',
            controller: 'ProfileCtrl',
            controllerAs: 'ctrl'
        })
        // change password
        .state('page.changePassword', {
            url: '/changePassword',
            templateUrl: 'templates/changePassword.html',
            controller: 'ChangePasswordCtrl',
            controllerAs: 'ctrl'
        })
        // edit profile
        .state('page.editProfile', {
            url: '/editProfile',
            templateUrl: 'templates/editProfile.html',
            controller: 'EditProfileCtrl',
            controllerAs: 'ctrl',
            resolve: {
                // get needed values for the form
                profileAttributes: ['$q', 'UserService', function ($q, UserService) {
                    var promises = {
                        carSharingServices: UserService.getCarSharingServices(),
                        educationLevels: UserService.getEducationLevels(),
                        employments: UserService.getEmployments(),
                        fuels: UserService.getFuels(),
                        travelDocuments: UserService.travelDocuments()
                    };
                    // return a promise that depends on all the promises stated before
                    return $q.all(promises);
                }]
            }
        });

    // configure html5 to get links working on jsfiddle
    //$locationProvider.html5Mode(true);
}]);

app.run(['$rootScope', '$state', function ($rootScope, $state) {
    // check the status of the user
    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        var user = $rootScope.user;
        // if the user is not logged in, continue without problems
        if (!user) {
            return;
        }
        switch (user.status.value) {
            // incomplete profile, send to edit profile
            case "INCOMPLETE":
                console.log('incomplete profile');
                if (toState.name != 'page.editProfile') {
                    // TODO show warning
                    event.preventDefault();
                    $state.go('page.editProfile');
                }
                break;
            // user needs to confirm email
            case "NOT_CONFIRMED":
                // TODO
                console.log('user not confirmed mail');
                break;
        }
    });
}]);
