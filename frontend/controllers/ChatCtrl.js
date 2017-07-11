var app = angular.module('App');

app.controller('ChatCtrl', ['$scope', '$uibModal', '$stateParams', '$localStorage', 'ChatService', function ($scope, $uibModal, $stateParams, $localStorage, chatService) {
    this.template = "templates/popovers/popoverTemplate.html";
    
    this.messages = [];
    this.msg_text = "";
    this.msg_image = null;

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
    

    this.sendMessage = ()=> {
        if (this.msg_text != "") {
            var newMsg = {
                text: this.msg_text,
                image: this.msg_image
            };

            chatService.sendMessage(topicId, newMsg);
            this.msg_text = "";
            this.msg_image = null;
        }
    }


    this.openNewWarningModal = function (size, parentSelector) {
        var modalInstance = $uibModal.open({
            templateUrl: 'templates/modals/newAlertModal.html',
            controller: 'NewAlertModalCtrl',
            controllerAs: 'ctrl',
            size: 'lg'
        });

        modalInstance.result.then(function (selectedItem) {
            $ctrl.selected = selectedItem;
            }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    this.openAddPictureModal = (size, parentSelector) => {
        var modalInstance = $uibModal.open({
            templateUrl: 'templates/modals/addPictureModal.html',
            controller: 'AddPictureModalCtrl',
            controllerAs: 'ctrl',
            size: 'lg'
        });

        modalInstance.result.then((imageString) => {
            this.msg_image = imageString;
            },function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

}]);