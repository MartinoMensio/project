var app = angular.module('App');

app.controller('ChatCtrl', ['$scope', '$uibModal', function ($scope, $uibModal) {
    this.messages = [];
    this.msg_text = "";

    var ms1 = {
        username: "Alessio",
        timestamp: new Date(),
        text: "blablavla",
        side: "right"
    }
    var ms2 = {
        username: "Alessio",
        timestamp: new Date(),
        text: "blablavla",
        side: "left"
    }
    var ms3 = {
        username: "Alessio",
        timestamp: new Date(),
        text: "blablavla",
        side: "right"
    }

    this.messages.push(ms1);
    this.messages.push(ms2);
    this.messages.push(ms3);

    this.sendMessage = function() {
        if (this.msg_text != "") {
            var newMsg = {
                username: "Alessio",
                timestamp: new Date(),
                text: this.msg_text,
                side: "right"
            };

            this.messages.push(newMsg);
            this.msg_text = "";
        }
    }


    this.openNewWarningModal = function (size, parentSelector) {
        //var parentElem = parentSelector ? angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;
        
        var modalInstance = $uibModal.open({
            /*ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',*/
            templateUrl: 'templates/modals/newWarningModal.html',
            /*controller: 'ModalInstanceCtrl',
            controllerAs: '$ctrl',*/
            size: 'lg'
            /*appendTo: parentElem,*/
            /*resolve: {
                items: function () {
                return $ctrl.items;
                }
            }*/
        });

        modalInstance.result.then(function (selectedItem) {
            $ctrl.selected = selectedItem;
            }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

}]);