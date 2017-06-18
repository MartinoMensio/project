var app = angular.module('App');

app.controller('ChatCtrl', ['$scope', function ($scope) {
    this.messages = [];
    this.msg_text = "";

    var ms1 = {
        username: "Alessio",
        timestamp: new Date(),
        text: "blablavla",
        side: "right"
    }
    var ms2 = {
        username: "Alessio",
        timestamp: new Date(),
        text: "blablavla",
        side: "left"
    }
    var ms3 = {
        username: "Alessio",
        timestamp: new Date(),
        text: "blablavla",
        side: "right"
    }

    this.messages.push(ms1);
    this.messages.push(ms2);
    this.messages.push(ms3);

    this.sendMessage = function() {
        if (this.msg_text != "") {
            var newMsg = {
                username: "Alessio",
                timestamp: new Date(),
                text: this.msg_text,
                side: "right"
            };

            this.messages.push(newMsg);
            this.msg_text = "";
        }
    }


}]);