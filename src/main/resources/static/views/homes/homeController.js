"use strict";

var app = angular.module("myApp.homes", ["ngRoute"])

.config(['$routeProvider', function ($routeProvider) {
  $routeProvider
    .when('/home', {
      templateUrl: 'views/homes/home.html',
      controller: ''
    });
}]);