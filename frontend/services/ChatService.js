/*
 * CHAT SERVICE INTERFACE
 * 
 * This file contains the definition of the CHAT SERVICE interface.
 * It defines all the needed data structures and the methods for
 * accessing these data structures.
 * 
*/

var app = angular.module('App');

app.factory('ChatService', ['$http', function ($http) {
    var messages = [];     // the list of messages

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

    messages.push(ms1);
    messages.push(ms2);
    messages.push(ms3);


    // service interface definition
    return {
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
         * Return
         *  - void
        */
        sendMessage: function(message) {
            messages.push(message);

            // TODO use HTTP for sending the message to the server
        }
    }
}]);