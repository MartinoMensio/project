var app = angular.module('App');

app.controller('BusLinesCtrl', ['$scope', '$stateParams', 'leafletData', 'BusLinesService', 'geojson',
    function ($scope, $stateParams, leafletData, BusLinesService, geojson) {
        this.lines = [];
        this.lineId = $stateParams.id;


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

        if (geojson) {
            this.data = geojson;

            leafletData.getMap().then((map) => {
                map.fitBounds(this.data.latlngs);
            });
        }

        this.searchText = "";

        this.searchLines = () => {
            if (this.searchText === "") {
                return;
            }
            BusLinesService.getLinesByIdContaining(this.searchText).then((result) => {
                this.lines = result;
                this.showList = true;
            })
        }

        $scope.$on('leafletDirectiveMarker.click', (event, args) => {
            // TODO expand details of marker
        });
    }
]);