/**
 * Created by Evgenia on 5/24/2014.
 */

var researchApplication = angular.module('research', ['ngRoute', 'restangular']);

researchApplication.config(function ($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'views/main.html',
            controller: 'MainController',
            resolve: MainController.resolve
        })
        .otherwise({
            redirectTo: '/'
        });
});

researchApplication.config(function (RestangularProvider) {
    RestangularProvider.setBaseUrl("http://localhost:8080/research/server/");
});