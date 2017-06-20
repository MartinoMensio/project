/*
 * CHAT SERVICE INTERFACE
 * 
 * This file contains the definition of the CHAT SERVICE interface.
 * It defines all the needed data structures and the methods for
 * accessing these data structures.
 * 
*/

var app = angular.module('App');

app.factory('ChatService', ['$http', '$q', '$localStorage', '$stomp', '$log', function ($http, $q, $localStorage, $stomp, $log) {
    var webEndpoint = "http://localhost:8888/";

    var connectHeaders = {};
    var chatEndpoint = "http://localhost:8888/chat";
    var roomEndpoint = "/app/";

    
    // Configuring the WebSocket for console logging
    $stomp.setDebug(function (args) {
      $log.debug(args)
    });


    // service interface definition
    return {
        /* Returns the list of all available topics
         * Parameters:
         *  - void
         * Return
         *  - a promise that contains the list of topics
         * */
        getTopics: function() {
            var deferred = $q.defer();

            $http.get(webEndpoint + 'api/open/topics').then(function (result) {
                // get secceded
                deferred.resolve(result.data);
            }, function (error) {
                // get failed
                deferred.reject(error);
            });

            return deferred.promise;
        },

        /* Registers a callback that is called whenever a new message
         * on the specified topic is received.
         * Parameters:
         *  - the topic id
         *  - the callback: function callback(msg) { ... }
         * Return
         *  - void
        */
        connect: function(topicId, callback) {
            var jwtParam = "?jwt=Bearer " + $localStorage.token;

            $stomp.connect(chatEndpoint + jwtParam, connectHeaders).then(function (frame) {
                var subscription = $stomp.subscribe('/topic/' + topicId, function (message, headers, res) {
                        // Parse the message and add it to the list of messages 
                        var receivedMsg = {
                            username: message.userNickname,
                            timestamp: message.sendingTime,
                            text: message.text,
                            side: "left"
                        }

                        callback(receivedMsg);
                    },
                    { }
                );
            });
        },

        /* Sends a message to the server adding it to the list of messages
         * Parameters:
         *  - topicId: the id of the topic on which the massege has to be sent
         *  - message: an object that represents the message
         *      message = {
                    username: string,
                    timestamp: date,
                    text: string,
                    side: right | left
                };
         * Return
         *  - void
        */
        sendMessage: function(topicId, message) {
            // send the message to the server via the WebSocket
            $stomp.send(roomEndpoint + topicId, {
                    content : message.text,
                    image: ""
                },
                {}
            );
        }
    }
}]);