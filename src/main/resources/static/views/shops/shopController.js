"use strict";

var app = angular
    .module("myApp.shops", ["ngRoute"])

.config([
    "$routeProvider",
    function($routeProvider) {
        $routeProvider
            .when("/listShops", {
                templateUrl: "views/shops/listShops.html",
                controller: "shopCtrl",
            })
            .when("/listShopsActivated", {
                templateUrl: "views/shops/listShops.html",
                controller: "shopCtrl",
            })
            .when("/listShopsNotActivated", {
                templateUrl: "views/shops/listShops.html",
                controller: "shopCtrl",
            })
            .when("/listShopsBlock", {
                templateUrl: "views/shops/listShops.html",
                controller: "shopCtrl",
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

.controller("shopCtrl", [
    "shopFactory",
    "$scope",
    "$http",
    function(shopFactory, $scope, $http) {
        $scope.shops = [];
        $scope.shop = {};
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

        console.log('URL: ' + resultUrl)

        if (resultUrl == "listShops") {
            $scope.titleContent = 'tất cả';
            shopFactory.getShops().then(
                (data) => {
                    $scope.shops = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }

        if (resultUrl == "listShopsActivated") {
            $scope.titleContent = 'đã kích hoạt';
            shopFactory.getShopsActivated().then(
                (data) => {
                    $scope.shops = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }

        if (resultUrl == "listShopsNotActivated") {
            $scope.titleContent = 'chưa kích hoạt';
            shopFactory.getShopsNotActivated().then(
                (data) => {
                    $scope.shops = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }

        if (resultUrl == "listShopsBlock") {
            $scope.titleContent = 'bị khóa';
            shopFactory.getShopsBlock().then(
                (data) => {
                    $scope.shops = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }

        $scope.getShop = function(id) {
            shopFactory.getShop(id).then(
                (data) => {
                    $scope.shop = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        };

        $scope.update = function(id) {
            if ($scope.myForm.files[0] != null) {
                $scope.doUploadFile();
                $scope.shop.picture = $scope.myForm.files[0].name;
            }

            shopFactory.updateShop(id, $scope.shop).then(
                (data) => {
                    $scope.shop = data;
                    alert("Update shop successfully!");
                    location.reload();
                },
                (reason) => {
                    alert("Update shop failed!\nError: " + reason);
                }
            );
        };

        $scope.delete = function(id) {
            shopFactory.deleteShop(id).then(
                (data) => {
                    $scope.shop = data;
                    alert("Delete shop successfully!");
                    location.reload();
                },
                (reason) => {
                    alert("Delete shop failed!\nError: " + reason);
                    location.reload();
                }
            );
        };

        $scope.block = function(id) {
            shopFactory.blockShop(id, $scope.shop).then(
                (data) => {
                    $scope.shop = data;
                    alert("Block shop successfully!");
                    location.reload();
                },
                (reason) => {
                    alert("Block shop failed!\nError: " + reason);
                    location.reload();
                }
            );
        };

        $scope.active = function(id) {
            console.log($scope.statusActive);
            shopFactory.activeShop(id, $scope.shop, $scope.statusActive).then(
                (data) => {
                    $scope.shop = data;
                    alert("Active shop successfully!");
                    location.reload();
                },
                (reason) => {
                    alert("Active shop failed!\nError: " + reason);
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

        // ********************
        //  LIST SHOP
        // ********************

        $scope.propertyName = "name";
        $scope.sortReverse = false;

        $scope.sortBy = function(propertyName) {
            $scope.reverse =
                $scope.propertyName === propertyName ? !$scope.reverse : false;
            $scope.propertyName = propertyName;
        };
    },
]);