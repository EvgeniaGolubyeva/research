'use strict';

function MainController(bpUserData,
                        bpAppData,
                        isAuthenticated,
                        authenticationService,
                        bloodPressureService,
                        $scope, $window, $route) {

    //------- web sockets --------
    var onChange = function() {
        console.log("reload");
        $route.reload();
    };

    bloodPressureService.watchChanges(onChange);
    $scope.$on('$destroy', bloodPressureService.unwatchChanges(onChange));
    //-------

    //---------radio buttons -------------
    $scope.data = bpUserData;
    $scope.dataType = "userType";

    $scope.changeDataType = function () {
        $scope.data = $scope.dataType == "userType" ? bpUserData : bpAppData;
    };
    //-------

    $scope.isAuthenticated = isAuthenticated;

    $scope.iHealthLogin = function() {
        authenticationService.iHealthLogin().then(
            function(response) {
                $window.location.href = response;
            }
        );
    };

    $scope.signOut = function() {
        authenticationService.signOut().then(
            function(response) {
                $route.reload();
            }
        );
    };
}

MainController.resolve = {
    bpUserData: function(bloodPressureService) {
        return bloodPressureService.getUserData();
    },
    bpAppData: function(bloodPressureService) {
        return bloodPressureService.getAppData();
    },
    isAuthenticated: function(authenticationService) {
        return authenticationService.isAuthenticated();
    }
};

angular.module('research').controller('MainController', MainController);