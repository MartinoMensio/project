var app = angular.module('App');

app.controller('ChatCtrl', ['$scope', '$uibModal', '$stateParams', '$localStorage', 'ChatService', 'AlertsService', function ($scope, $uibModal, $stateParams, $localStorage, ChatService, AlertsService) {
    this.template = "templates/popovers/popoverTemplate.html";

    var local_icons = {
        default_icon: {},
        leaf_icon: {
            iconUrl: 'icons/alert1.png',
            iconSize:     [50, 50], // size of the icon
            iconAnchor:   [30, 30], // point of the icon which will correspond to marker's location
            popupAnchor:  [0, 0] // point from which the popup should open relative to the iconAnchor
        },
    };

    // define the map status: shown/not shown
    this.showMap = false;

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

    this.messages = [];
    this.msg = {
        content: "",
        image: null,
        alertId: null
    }

    var topicId = $stateParams.topicId; // get the topic id from the app state


    // Retrieve the last messages from the topic
    ChatService.getLastMessages(topicId).then((result) => {
        this.messages = result;
    });


    // Define the callback that is called when a new message is received
    var addMessage = (message) => {
        this.messages.push(message);
        $scope.$apply();
    }

    // Register the topic and the callback
    ChatService.connect(topicId, addMessage);


    this.sendMessage = () => {
        if (this.msg.content != "") {
            // check the alerts
            if (!this.msg.alertId) {
                // no alert is selected, check manually the text to find hashtag
                const hashtag = AlertsService.findHashtag(this.msg.content);
                if (hashtag) {
                    // an hashtag is present in the text
                    this.openNewAlertModal(hashtag);
                    // stop the flow. The modal when closed will call again the sendMessage function
                    return;
                }
            }

            ChatService.sendMessage(topicId, this.msg).then((result) => {
                this.msg = {
                    content: "",
                    image: null,
                    alertId: null
                };
                this.alert = null;
            });
        }
    }

    this.mapToggle = () => {
        this.showMap = !this.showMap;
        // get the list of all teh alerts
        AlertsService.getAlerts().then((result) => {
            this.alerts = result; // show the alert list

            this.hashtag = result.hashtag;
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
                //marker.iconUrl="icons/alert.png";
                marker.icon= local_icons.leaf_icon,
                marker.message = '<div style="min-width:160px;"></div><h2>#'+marker.hashtag+'</h2> <h5>Vote Here</h5><input-stars ng-model="ctrl.markers['+index+'].newRating" max="5"></input-stars> </br><h5>Average</h5> <input-stars max="5" ng-model="ctrl.markers['+index+'].rating" readonly="true" allow-half ></input-stars>';
            }, this);
        });
    }

    // this function is called every time the message text changes 
    this.msg_text_changed = () => {
        // only search if there is not yet an alert linked
        if (!this.msg.alertId) {
            const hashtag = AlertsService.findHashtag(this.msg.content);
            if (hashtag) {
                AlertsService.getAlertsWithHashtag(hashtag).then((result) => {
                    // TODO display in dropdown
                    this.alertsResult = result;
                })
            }
        }
    };

    // this function is called when an alert in the dropdown is clicked
    this.alertClicked = (alert) => {
        // save the id in the message to be sent
        this.msg.alertId = alert.id;
        // and also save a copy of the alert (could be useful to display around message, if user wants to remove the linked alert he will click on X ??)
        this.alert = alert;
        this.alertsResult = null;
        // autocomplete the text
        this.msg.content = AlertsService.autocompleteHashtag(this.msg.content, this.alert.hashtag);
    };

    this.removeAlert = () => {
        this.alert = null;
        this.msg.alertId = null;
    };

    this.removeImage = () => {
        this.msg.image = null;
    }

    this.openNewAlertModal = (hashtag) => {
        var modalInstance = $uibModal.open({
            templateUrl: 'templates/modals/newAlertModal.html',
            controller: 'NewAlertModalCtrl',
            controllerAs: 'ctrl',
            size: 'lg',
            resolve: {
                alertTypes: AlertsService.getAlertTypes(),
                hashtag: function () {
                    return hashtag;
                }
            }
        });

        modalInstance.result.then((newAlert) => {
            AlertsService.saveNewAlert(newAlert).then((createdAlert) => {
                this.msg.alertId = createdAlert.id;

                ChatService.sendMessage(topicId, this.msg).then((result) => {
                    this.msg = {
                        content: "",
                        image: null,
                        alertId: null
                    };
                    this.alert = null;
                });
            })



        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    this.openAddPictureModal = (size, parentSelector) => {
        var modalInstance = $uibModal.open({
            templateUrl: 'templates/modals/addPictureModal.html',
            controller: 'AddPictureModalCtrl',
            controllerAs: 'ctrl',
            size: 'lg'
        });

        modalInstance.result.then((imageString) => {
            this.msg.image = imageString;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    this.selectAlert = (alertId) => {
        console.log("clicked alert " + alertId);
        this.showMap = true;
        // TODO open alert detail
    }

}]);