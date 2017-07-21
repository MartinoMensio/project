var app = angular.module('App');

app.controller('NewAlertModalCtrl', ['$rootScope', '$scope', '$uibModalInstance', 'AlertsService', 'GeocodingService', 'alertTypes', 'hashtag', function ($rootScope, $scope, $uibModalInstance, AlertsService, GeocodingService, alertTypes, hashtag) {

    this.alertTypes = alertTypes;
    this.alert = {};
    this.alert.alertTypeId = alertTypes[alertTypes.length - 1].id;

    this.alert.hashtag = hashtag;


    // on address change, use geocoding APIs
    this.addressChanged = () => {
        GeocodingService.getLocationFromString(this.alert.address).then((result) => {
            this.fullAddress = result.formatted_address;
            this.alert.lat = result.geometry.location.lat;
            this.alert.lng = result.geometry.location.lng;
        });
    };

    this.geolocate = () => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition((position) => {
                this.alert.lat = position.coords.latitude;
                this.alert.lng = position.coords.longitude;
                GeocodingService.reverseGeocode(this.alert.lat, this.alert.lng).then((result) => {
                    this.alert.address = result.formatted_address;
                    this.fullAddress = result.formatted_address;
                });
            }, (error) => {
                $rootScope.$emit('error', { message: "You denied access to geolocation" });
            });
        }
    };

    this.save = () => {
        // take the full parsed address
        this.alert.address = this.fullAddress;
        $uibModalInstance.close(this.alert);
    };

    this.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}]);