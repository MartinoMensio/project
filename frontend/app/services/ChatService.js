/*
 * CHAT SERVICE INTERFACE
 * 
 * This file contains the definition of the CHAT SERVICE interface.
 * It defines all the needed data structures and the methods for
 * accessing these data structures.
 * 
*/

var app = angular.module('App');

app.factory('ChatService', ['$http', '$q', '$localStorage', '$stomp', '$log', '__env', function ($http, $q, $localStorage, $stomp, $log, __env) {
    var webEndpoint = __env.backend;

    var connectHeaders = {};
    var chatEndpoint = webEndpoint + 'chat';
    var roomEndpoint = '/app/';

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
        getTopics: function () {
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

        /* Returns the topic associated to the passed id
         * Parameters:
         *  - id - the topic id
         * Return
         *  - a promise that contains the topic
         * */
        getTopicById: function (id) {
            var deferred = $q.defer();

            $http.get(webEndpoint + 'api/open/topics/' + id).then(function (result) {
                // get secceded
                deferred.resolve(result.data);
            }, function (error) {
                // get failed
                deferred.reject(error);
            });

            return deferred.promise;
        },

        /* Returns the list of the last messages about the specified topic
         * Parameters:
         *  - the topic id
         * Return
         *  - a promise that contains the list of messages
        */
        getLastMessages: function (topicId) {
            var deferred = $q.defer();

            $http.get(webEndpoint + 'api/messages/' + topicId).then(function (result) {
                // get secceded
                deferred.resolve(result.data.content);
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
         *  - a promise
        */
        connect: function (topicId, callback) {
            if ($localStorage.token) {
                var jwtParam = "?jwt=Bearer " + $localStorage.token;

                return $stomp.connect(chatEndpoint + jwtParam, connectHeaders).then(function (frame) {
                    var subscription = $stomp.subscribe('/topic/' + topicId, function (message, headers, res) {
                        callback(message);
                    },
                        {}
                    );
                });
            } else {
                return $q.reject('no local storage token');
            }
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
        sendMessage: function (topicId, message) {
            // send the message to the server via the WebSocket
            return $stomp.send(roomEndpoint + topicId, message,
                {}
            );
        }
    }
}]);