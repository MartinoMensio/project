var app = angular.module('App');

app.controller('NewAlertModalCtrl', ['$scope', '$uibModalInstance', 'AlertsService', 'alertTypes', 'hashtag', function ($scope, $uibModalInstance, AlertsService, alertTypes, hashtag) {

    this.alertTypes = alertTypes;
    this.alert = {};
    this.alert.alertTypeId = alertTypes[alertTypes.length - 1].id;

    // TODO those are fake
    this.alert.lat = 4.5;
    this.alert.lng = 4.5;
    this.alert.hashtag = hashtag;


    // TODO on address change, use geolocation API
    
    this.save = () => {
        $uibModalInstance.close(this.alert);
    };

    this.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}]);