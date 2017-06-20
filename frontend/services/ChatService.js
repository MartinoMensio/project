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
    var jwtParam = "?jwt=Bearer " + $localStorage.token;
    var roomEndpoint = "/app/";
    var topicId = 1; // TODO get topic id from the app state
    
    var messages = [];     // the list of messages

    // Configuring the WebSocket
    $stomp.setDebug(function (args) {
      $log.debug(args)
    });

    $stomp.connect(chatEndpoint + jwtParam, connectHeaders).then(function (frame) {
        // frame = CONNECTED headers
        var subscription = $stomp.subscribe('/topic/' + topicId, function (message, headers, res) {
                // Parse the message and add it to the list of messages 
                var receivedMsg = {
                    username: message.userNickname,
                    timestamp: message.sendingTime,
                    text: message.text,
                    side: "left"
                }

                messages.push(receivedMsg);
            },
            { }
        );

        // Unsubscribe
        //subscription.unsubscribe()

        // Send message
        /*$stomp.send('/dest', {
          message: 'body'
        }, {
          priority: 9,
          custom: 42 // Custom Headers
        })*/

        // Disconnect
        /*$stomp.disconnect().then(function () {
          $log.info('disconnected')
        })*/
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

        /* Returns the list of all the stored messages
         * At the beginning it should contain the last 10 messages
         * Parameters:
         *  - void
         * Return
         *  - the list of messages
         * */
        getMessages: function() {
            return messages;
        },

        /* Sends a message to the server adding it to the list of messages
         * Parameters:
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
        sendMessage: function(message) {
            //messages.push(message);

            // TODO use HTTP for sending the message to the server
            $stomp.send(roomEndpoint + topicId, {
                    content : message.text,
                    image: ""
                },
                {}
            );
        }
    }
}]);