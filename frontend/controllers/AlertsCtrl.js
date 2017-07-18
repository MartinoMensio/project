var app = angular.module('App');

app.controller('AlertsCtrl', ['$scope', 'AlertsService', function($scope, alertsService) {
    $scope.rating = 0;
    $scope.ratings = [{
        current: 0,
        max: 5
    }];

    // get the list of all teh alerts
    alertsService.getAlerts().then((result) => {
        this.alerts = result; // show the alert list
        this.markers = result; // show the markers on the map

        // each marker display the 5 star for rating the alert
        this.markers.forEach((marker,index) => {
            marker.getMessageScope = ()=> { return $scope; }
            var initialization = true;
            $scope.$watch("ctrl.alerts["+index+"].rating", (newValue, oldValue)=> {
                if (initialization === true || newValue===0) {
                    initialization = false;
                    return;
                }
                this.vote(marker);
            });
            marker.message = '<h4>#'+marker.hashtag+'</h4><input-stars ng-model="ctrl.markers['+index+'].rating" max="5"></input-stars>'
        }, this);
    });

   


    // send the new vote to the database
    this.vote = function(marker){
        alertsService.voteAlert(marker.id,marker.rating);
    }
 

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
