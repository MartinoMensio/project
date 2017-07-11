var app = angular.module('App');

app.controller('NewAlertModalCtrl', ['$scope', '$uibModalInstance', 'AlertsService', function ($scope, $uibModalInstance, AlertsService) {
    
    AlertsService.getAlertTypes().then((result) => {
        this.alertTypes = result;
    });

    this.alert = {};

    // TODO on address change, use geolocation API
    
    this.save = () => {
        $uibModalInstance.close(this.alert);
    };

    this.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}]);