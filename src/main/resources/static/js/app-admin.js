'use strict';

angular
    .module('myApp', [
        'ngRoute',
        'myApp.homes',
        'myApp.users',
        'myApp.shops',
        'myApp.postings',
        'myApp.statistics',
        'myApp.version'
    ])
    .config(['$locationProvider',
        '$routeProvider', '$qProvider',
        function($locationProvider, $routeProvider, $qProvider) {
            $locationProvider.hashPrefix('!');
            // $qProvider.errorOnUnhandledRejections(false);
            $routeProvider.otherwise({ redirectTo: '/home' });
        }
    ]);