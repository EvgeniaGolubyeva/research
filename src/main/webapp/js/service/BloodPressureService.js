'use strict';

angular.module('research').service('bloodPressureService', function (Restangular) {

    var webSocket = new WebSocket('ws://localhost:8080/research/server/changes');
    var callbacks = [];

    webSocket.onmessage = function(event) {
       callbacks.forEach(function(callback) {
           callback();
       });
    };

    this.watchChanges = function(callback) {
        callbacks.push(callback);
    };

    this.unwatchChanges = function(callback) {
        _.pull(this.callbacks, callback);
    };

    this.getUserData = function () {
        return Restangular.all("bp").one("user").get();
    };

    this.getAppData = function () {
        return Restangular.all("bp").one("app").get();
    };
});