var app = angular.module('App');

app.controller('ChatTopicsCtrl', ['$scope', '$uibModal', 'ChatService', function ($scope, $uibModal, chatService) {
    chatService.getTopics().then((result) => {
        this.chatTopics = result;
    });
}]);