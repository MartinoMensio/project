var app = angular.module('App');

app.controller('BusLinesCtrl', ['$scope', 'leafletData', 'DataProvider',
    function ($scope, leafletData,  DataProvider) {
        this.lines = DataProvider.getLines();

        // map settings
        this.center = { 
            lat: 45.064, 
            lng: 7.681, 
            zoom: 13
        };
        this.data = {};
        this.bounds = {};
        this.defaults = {
            scrollWheelZoom: false
        };
        this.tiles = {
            name: 'MapBox',
            url: '//api.tiles.mapbox.com/v4/mapbox.streets/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw',
            type: 'xyz'
        };

        // variable that controls the visualization of the whole list
        this.showList = false;
        this.searchText = "";

        // control wether it is necessary to show or not the list when search text is written
        this.showResult = () => {
            if (this.searchText.localeCompare("") != 0) {
                this.showList = true;
            }
            else {
                this.showList = false;
            }
        };

        // reset the map to the initial state and clear the input text
        this.clearResult = () => {
            this.searchText = "";
            this.data = {};

            this.center = { 
                lat: 45.064, 
                lng: 7.681, 
                zoom: 13
            };
        }

        this.showLine = (lineId) => {
            this.searchText = lineId;

            this.data = DataProvider.getLineByIdAsGeoJson(lineId);

            leafletData.getMap().then((map) => {
                map.fitBounds(this.data.latlngs);
            });
        };
    }
]);