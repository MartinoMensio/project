var app = angular.module('App');

app.controller('BusLinesCtrl', ['$scope', 'leafletData', 'DataProvider', '$stateParams',
    function ($scope, leafletData,  DataProvider, $stateParams) {
        var lineId =  $stateParams.lineId;
        this.lines = DataProvider.getLines();

        this.tiles = {
            name: 'MapBox',
            url: '//api.tiles.mapbox.com/v4/mapbox.streets/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw',
            type: 'xyz'
        };

        // define the map centering
        this.center = { 
            lat: 45.064, 
            lng: 7.681, 
            zoom: 13
        }

        // variable that controls the visualization of the whole list
        this.showList = false;
        this.searchText = "";
        this.buttonText = "Show all";

        if (lineId) {
            this.data = DataProvider.getLineByIdAsGeoJson(lineId);
            var data = this.data;

            leafletData.getMap().then(function(map) {
                map.fitBounds(data.latlngs);
            });
        }

        // control wether it is necessary to show or not the whole list
        this.showAll = () => {
            if (this.searchText.localeCompare("") != 0) {
                this.searchText = "";
                this.showList = true;
                this.buttonText = "Hide";
            }
            else {
                if (this.showList == true) {
                    this.showList = false;
                    this.buttonText = "Show all";
                }
                else {
                    this.showList = true;
                    this.buttonText = "Hide";
                }
            }
        };

        // control wether it is necessary to show or not the list when search text is written
        this.showResult = () => {
            if (this.searchText.localeCompare("") != 0) {
                this.showList = true;
                this.buttonText = "Show all";
            }
            else {
                this.showList = false;
            }
        };

        this.toggleResult = () => {
            this.showList = !this.showList;
        };
    }
]);