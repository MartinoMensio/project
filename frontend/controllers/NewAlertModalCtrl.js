var app = angular.module('App');

app.controller('NewAlertModalCtrl', ['$scope', '$uibModalInstance', 'AlertsService', function ($scope, $uibModalInstance, AlertsService) {
    
    AlertsService.getAlertTypes().then((result) => {
        this.alertTypes = result;
    });

    this.alert = {};
    this.alert.alertType = 1;
    this.alert.lat = 4.5;
    this.alert.lng = 4.5;
    this.alert.hashtag = "test";


    // TODO on address change, use geolocation API
    
    this.save = () => {
        $uibModalInstance.close(this.alert);
    };

    this.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}]);