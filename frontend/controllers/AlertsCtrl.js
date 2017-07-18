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
            // watch added to check if a new rate is inserted by the user
            $scope.$watch("ctrl.alerts["+index+"].newRating", (newValue, oldValue)=> {
                if (initialization === true || newValue===0) {
                    initialization = false;
                    return;
                }
                this.vote(marker);
            });
            // 5 starts used by the user for rating the signalization and 5 stars readonly for the rating avg
            marker.message = '<div style="min-width:160px;"></div><h2>#'+marker.hashtag+'</h2> <h5>Vote Here</h5><input-stars ng-model="ctrl.markers['+index+'].newRating" max="5"></input-stars> </br><h5>Average</h5> <input-stars max="5" ng-model="ctrl.markers['+index+'].rating" readonly="true" allow-half ></input-stars>'
        }, this);
    });

    // send the new vote to the database
    this.vote = function(marker){
        // send the vote and then modify dynamically the avg value
        alertsService.voteAlert(marker.id,marker.newRating).then(result=>{
            marker.rating = result.rating;
        });
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
