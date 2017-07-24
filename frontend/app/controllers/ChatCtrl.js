var app = angular.module('App');

app.controller('ChatCtrl', ['$scope', '$uibModal', '$stateParams', '$localStorage', 'leafletData', 'ChatService', 'AlertsService', 'topic', 'messages', function ($scope, $uibModal, $stateParams, $localStorage, leafletData, ChatService, AlertsService, topic, messages) {
    this.template = "templates/popovers/popoverTemplate.html";
    var topicId = $stateParams.topicId; // get the topic id from the app state
    this.topic = topic;
    this.messages = messages;

    var local_icons = {
        generic_alert_icon: {
            iconUrl: 'icons/alert.png',
            iconSize: [50, 50], // size of the icon
            iconAnchor: [30, 30], // point of the icon which will correspond to marker's location
            popupAnchor: [0, 0] // point from which the popup should open relative to the iconAnchor
        },
        traffic_icon: {
            iconUrl: 'icons/traffic.png',
            iconSize: [50, 50], // size of the icon
            iconAnchor: [30, 30], // point of the icon which will correspond to marker's location
            popupAnchor: [0, 0] // point from which the popup should open relative to the iconAnchor
        },
        broken_bus_icon: {
            iconUrl: 'icons/broken-bus.png',
            iconSize: [50, 50], // size of the icon
            iconAnchor: [30, 30], // point of the icon which will correspond to marker's location
            popupAnchor: [0, 0] // point from which the popup should open relative to the iconAnchor
        },
        accident_icon: {
            iconUrl: 'icons/accident.png',
            iconSize: [50, 50], // size of the icon
            iconAnchor: [30, 30], // point of the icon which will correspond to marker's location
            popupAnchor: [0, 0] // point from which the popup should open relative to the iconAnchor
        },
        road_works_icon: {
            iconUrl: 'icons/work_in_progress.png',
            iconSize: [50, 50], // size of the icon
            iconAnchor: [30, 30], // point of the icon which will correspond to marker's location
            popupAnchor: [0, 0] // point from which the popup should open relative to the iconAnchor
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

    this.msg = {
        content: "",
        image: null,
        alertId: null
    }

    this.updateAlerts = () => {
        // get the list of all teh alerts
        return AlertsService.getAlerts().then((result) => {
            this.alerts = result; // show the alert list

            this.markers = result; // show the markers on the map

            // each marker display the 5 star for rating the alert
            this.markers.forEach((marker, index) => {
                marker.getMessageScope = () => { return $scope; }
                marker.newRating = -1;
                // 5 starts used by the user for rating the signalization and 5 stars readonly for the rating avg
                if (this.alerts[index].alertType.name === "Traffic") {
                    marker.icon = local_icons.traffic_icon;
                }
                else if (this.alerts[index].alertType.name === "Broken bus") {
                    marker.icon = local_icons.broken_bus_icon;
                }
                else if (this.alerts[index].alertType.name === "Accident") {
                    marker.icon = local_icons.accident_icon;
                }
                else if (this.alerts[index].alertType.name === "Road works") {
                    marker.icon = local_icons.road_works_icon;
                } else {
                    // generic alert
                    marker.icon = local_icons.generic_alert_icon;
                }
                marker.message = '<div style="min-width:160px;"></div><h2>#' + marker.hashtag
                    + '</h2><h5>Activation Date: {{' + marker.activationDate + ' | date:"HH:mm:ss dd-MM-yyyy"}}'
                    + '</h5><h5>Address: ' + marker.address
                    + '</h5><h5>Added by: ' + marker.userNickname
                    + '</h5> <h5>Vote Here</h5> <input-stars ng-click="ctrl.vote(' + marker.id + ')" ng-model="ctrl.markers[' + index + '].newRating" max="5"></input-stars>'
                    + '<br /> <h5>Average</h5> <input-stars max="5" ng-model="ctrl.markers[' + index + '].rating" readonly="true" allow-half ></input-stars>'
                    + '<br /><button ng-click="ctrl.referAlert(' + index + ')" class="btn btn-secondary">refer</button>';
            });
        });

    }

    this.referAlert = (index) => {
        alert = this.markers[index];
        // set the text
        this.msg.content += '#' + alert.hashtag;
        // and link the alert
        this.alertClicked(alert);
    }

    // Define the callback that is called when a new message is received
    var addMessage = (messageId) => {
        ChatService.getMessageById(messageId).then((message) => {
            this.messages.push(message);
            if (this.showMap === true) {
                this.updateAlerts();
            }
        });
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

    this.mapToggle = (forcedValue) => {
        if (forcedValue === undefined) {
            this.showMap = !this.showMap;
        } else {
            this.showMap = forcedValue;
        }

        // return the promise (thenable)
        return this.updateAlerts();
    }

    $scope.$on('leafletDirectiveMarker.click', (event, args) => {
        // get the user rating and update last view time
        AlertsService.getUserRatingToAlert(args.model.id).then((result) => {
            args.model.newRating = result.vote;
        });
    });

    // this function is called every time the message text changes 
    this.msg_text_changed = () => {
        // only search if there is not yet an alert linked
        if (!this.msg.alertId) {
            const hashtag = AlertsService.findHashtag(this.msg.content);
            if (hashtag) {
                AlertsService.getAlertsWithHashtag(hashtag).then((result) => {
                    // display in dropdown
                    this.alertsResult = result;
                })
            } else {
                this.alertsResult = null;
            }
        }
    };

    // send the new vote to the database
    this.vote = function (markerId) {
        const marker = this.markers.find((el) => el.id == markerId);
        if (marker.newRating < 1 || marker.newRating > 5) {
            return null;
        }
        // send the vote and then modify dynamically the avg value
        AlertsService.voteAlert(marker.id, marker.newRating).then(result => {
            marker.rating = result.rating;
        });
    }

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
            console.log('Modal dismissed at: ' + new Date());
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
            console.log('Modal dismissed at: ' + new Date());
        });
    };

    this.selectAlert = (alertId) => {
        // open the map
        this.mapToggle(true).then(() => {
            // show the marker details
            this.markers.forEach((marker) => {
                if (marker.id === alertId) {
                    marker.focus = true;
                    AlertsService.getUserRatingToAlert(marker.id).then((result) => {
                        marker.newRating = result.vote;
                    });
                }
                else {
                    marker.focus = false;
                }
            });
        })
    };

}]);