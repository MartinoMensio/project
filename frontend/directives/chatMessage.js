var app = angular.module('App');

app.directive('message', function() {
    return {
        scope: {
            userImageUrl: '@',
            imageUrl: '@',
            username: '@',
            timestamp: '@',
            text: '@',
            side: '@'
        },
        link: function link(scope, element, attrs) {
            if (scope.side == "left") {
                scope.li_pos = "left";
                scope.pull = "pull-left";
                scope.username_pos = "";
                scope.timestamp_pos = "pull-right";
            }
            else if (scope.side == "right") {
                scope.li_pos = "right";
                scope.pull = "pull-right";
                scope.username_pos = "pull-right";
                scope.timestamp_pos = "align-right";
            }
        },
        templateUrl: "../templates/chatMessage.html"
    };
});