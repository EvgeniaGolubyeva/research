'use strict';

angular.module('research').service('authenticationService', function (Restangular, $http) {
    this.iHealthLogin = function () {
        return Restangular.all("authorization").one("iHealthLabsAuthorizationUrl").get();
    };

    this.isAuthenticated = function () {
        return Restangular.all("authorization").one("isAuthenticated").get().then(
            function(response) {
                return response === "true"
            }
        );
    };

    this.signOut = function() {
        return Restangular.all("authorization").one("signOut").post();
    }
});