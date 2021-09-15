"use strict";

var app = angular
    .module("myApp.postings", ["ngRoute"])

.config([
    "$routeProvider",
    function($routeProvider) {
        $routeProvider
            .when("/listPostings", {
                templateUrl: "views/postings/listPostings.html",
                controller: "postingCtrl",
            })
            .when("/listPostingsUnapproved", {
                templateUrl: "views/postings/listPostings.html",
                controller: "postingCtrl",
            })
            .when("/listPostingsApproved", {
                templateUrl: "views/postings/listPostings.html",
                controller: "postingCtrl",
            })
            .when("/listPostingsSold", {
                templateUrl: "views/postings/listPostings.html",
                controller: "postingCtrl",
            })
            .when("/listPostingsBlock", {
                templateUrl: "views/postings/listPostings.html",
                controller: "postingCtrl",
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

.controller("postingCtrl", [
    "postingFactory",
    "$scope",
    "$http",
    function(postingFactory, $scope, $http) {
        $scope.postings = [];
        $scope.posting = {};
        $scope.titleContent = '';

        var url = window.location.href;
        var arr = url.split("/");
        var resultUrl = arr[4];

        $scope.myForm = {
            description: "",
            files: [],
        };

        $scope.currentPage = 1;
        $scope.itemsPerPage = 5;

        $scope.repaginate = function() {
            $scope.currentPage = 1;
        };

        console.log("URL: " + resultUrl);

        if (resultUrl == "listPostings") {
            $scope.titleContent = 'tất cả';
            postingFactory.getPostings().then(
                (data) => {
                    $scope.postings = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }
        if (resultUrl == "listPostingsUnapproved") {
            $scope.titleContent = 'chưa duyệt';
            postingFactory.getPostingsUnapproved().then(
                (data) => {
                    $scope.postings = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }
        if (resultUrl == "listPostingsApproved") {
            $scope.titleContent = 'đã duyệt';
            postingFactory.getPostingsApproved().then(
                (data) => {
                    $scope.postings = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }
        if (resultUrl == "listPostingsSold") {
            $scope.titleContent = 'đã bán';
            postingFactory.getPostingsSold().then(
                (data) => {
                    $scope.postings = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }
        if (resultUrl == "listPostingsBlock") {
            $scope.titleContent = 'bị khóa';
            postingFactory.getPostingsBlock().then(
                (data) => {
                    $scope.postings = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }

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