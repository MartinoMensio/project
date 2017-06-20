var app = angular.module('App');

app.controller('ChatCtrl', ['$scope', '$uibModal', '$stateParams', '$localStorage', 'ChatService', function ($scope, $uibModal, $stateParams, $localStorage, chatService) {
    this.template = "templates/popovers/popoverTemplate.html";
    
    this.messages = [];
    this.msg_text = "";

    var topicId = $stateParams.topicId; // get the topic id from the app state


    // Retrieve the last messages from the topic
    chatService.getLastMessages(topicId).then((result) => {
        this.messages = result;
    });


    // Define the callback that is called when a new message is received
    var addMessage = (message) => {
        this.messages.push(message);
        $scope.$apply();
    }

    // Register the topic and the callback
    chatService.connect(topicId, addMessage);
    

    this.sendMessage = function() {
        if (this.msg_text != "") {
            var newMsg = {
                username: "Alessio",
                timestamp: new Date(),
                text: this.msg_text,
                side: "right"
            };

            chatService.sendMessage(topicId, newMsg);
            this.msg_text = "";
        }
    }


    this.openNewWarningModal = function (size, parentSelector) {
        var modalInstance = $uibModal.open({
            templateUrl: 'templates/modals/newWarningModal.html',
            controller: 'NewWarningModalCtrl',
            controllerAs: 'ctrl',
            size: 'lg'
        });

        modalInstance.result.then(function (selectedItem) {
            $ctrl.selected = selectedItem;
            }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    this.openAddPictureModal = function () {
        var modalInstance = $uibModal.open({
            templateUrl: 'templates/modals/addPictureModal.html',
            controller: 'AddPictureModalCtrl',
            controllerAs: 'ctrl',
            size: 'lg'
        });

        modalInstance.result.then(function (selectedItem) {
            $ctrl.selected = selectedItem;
            },function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

}]);