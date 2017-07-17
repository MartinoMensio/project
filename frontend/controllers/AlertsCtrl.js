var app = angular.module('App');

app.controller('AlertsCtrl', ['$scope', 'AlertsService', function($scope, alertsService) {
    alertsService.getAlerts().then((result) => {
        this.alerts = result; // show the alert list
        this.markers = result; // show the markers on the map

       this. markers.forEach(function(marker) {
            marker.message = '<div class="rating">  <span class="fa fa-star-o"></span><span class="fa fa-star-o"></span><span class="fa fa-star-o"></span><span class="fa fa-star-o"></span><span class="fa fa-star-o"></span> </div>'
        }, this);

    });

    // initialize the map
    this.center = {
        lat: 45.064,
        lng: 7.681,
        zoom: 13
    };
    this.defaults = {
        scrollWheelZoom: false
    };
    this.markers = {};
    this.events = {
        map: {
            enable: ['zoomstart', 'drag', 'click', 'mousemove'],
            logic: 'emit'
        }
    };
    this.tiles = {
            name: 'MapBox',
            url: '//api.tiles.mapbox.com/v4/mapbox.streets/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw',
            type: 'xyz'
    };

}]);
