"use strict";

var app = angular
    .module("myApp.statistics", ["ngRoute"])

.config([
    "$routeProvider",
    function($routeProvider) {
        $routeProvider.when("/statistics", {
            templateUrl: "views/statistics/statistic.html",
            controller: "statisticCtrl",
        });
    },
])

.directive("fileModel", [
    "$parse",
    function($parse) {
        return {
            restrict: "A",
            link: function(scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind("change", function() {
                    scope.$apply(function() {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            },
        };
    },
])

.directive("date", function(dateFilter) {
    return {
        require: "ngModel",
        link: function(scope, elm, attrs, ctrl) {
            var dateFormat = attrs["date"] || "yyyy-MM-dd";

            ctrl.$formatters.unshift(function(modelValue) {
                return dateFilter(modelValue, dateFormat);
            });
        },
    };
})

.controller("statisticCtrl", [
    "postingFactory",
    "$scope",
    "$http",
    function(postingFactory, $scope, $http) {
        $scope.postings = [];
        $scope.posting = {};
        $scope.obj = {};
        $scope.arr = [];

        $scope.myForm = {
            description: "",
            files: [],
        };

        $scope.currentPage = 1;
        $scope.itemsPerPage = 5;

        $scope.repaginate = function() {
            $scope.currentPage = 1;
        };


        postingFactory.getPostings().then(
            (data) => {
                $scope.postings = data;
                $scope.arr = data;
            },
            (reason) => {
                console.log(reason);
            }
        );

        $scope.obj.check = function() {
            console.log("called me", $scope.fromDate);
            var d1 = new Date($scope.fromDate);
            var d2 = new Date($scope.toDate);
            if (d1.getTime() > d2.getTime()) {
                console.log("error");
            } else {
                $scope.postings = [];
                for (const i in $scope.arr) {
                    var cmp = new Date($scope.arr[i].date);
                    console.log("called me d1", d1.getTime());
                    console.log("called me d2", d2.getTime());
                    console.log("called me cmp", cmp.getTime());
                    if (d1.getTime() <= cmp.getTime() && d2.getTime() >= cmp.getTime()) {
                        console.log("Val in between");
                        $scope.postings.push($scope.arr[i]);
                    }
                }
            }
        };

        $scope.refresh = function() {
            location.reload();
        };

        $scope.getPosting = function(id) {
            postingFactory.getPosting(id).then(
                (data) => {
                    $scope.posting = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        };

        $scope.update = function(id) {
            postingFactory
                .updateStatusPosting(id, $scope.posting.status, $scope.posting)
                .then(
                    (data) => {
                        $scope.posting = data;
                        alert("Update status posting successfully!");
                        location.reload();
                    },
                    (reason) => {
                        alert("Update status posting failed!\nError: " + reason);
                    }
                );
        };

        $scope.delete = function(id) {
            postingFactory.deletePosting(id).then(
                (data) => {
                    $scope.posting = data;
                    alert("Delete posting successfully!");
                    location.reload();
                },
                (reason) => {
                    alert("Delete posting failed!\nError: " + reason);
                    location.reload();
                }
            );
        };

        $scope.block = function(id) {
            postingFactory.blockPosting(id, $scope.posting).then(
                (data) => {
                    $scope.posting = data;
                    alert("Block posting successfully!");
                    location.reload();
                },
                (reason) => {
                    alert("Block posting failed!\nError: " + reason);
                    location.reload();
                }
            );
        };

        $scope.doUploadFile = function() {
            var url = "http://localhost:8080/rest/uploadMultiFiles";

            var data = new FormData();

            data.append("description", "upload");
            data.append("files", $scope.myForm.files[0]);

            var config = {
                transformRequest: angular.identity,
                transformResponse: angular.identity,
                headers: {
                    "Content-Type": undefined,
                },
            };

            $http.post(url, data, config);
        };

        $scope.propertyName = "name";
        $scope.sortReverse = false;

        $scope.sortBy = function(propertyName) {
            $scope.reverse =
                $scope.propertyName === propertyName ? !$scope.reverse : false;
            $scope.propertyName = propertyName;
        };
    },
]);