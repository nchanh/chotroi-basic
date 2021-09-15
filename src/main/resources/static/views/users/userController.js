"use strict";

var app = angular
    .module("myApp.users", ["ngRoute", 'ui.bootstrap'])

.config([
    "$routeProvider",
    function($routeProvider) {
        $routeProvider
            .when("/listUsers", {
                templateUrl: "views/users/listUsers.html",
                controller: "userCtrl",
            })
            .when("/listUsersActivated", {
                templateUrl: "views/users/listUsers.html",
                controller: "userCtrl",
            })
            .when("/listUsersNotActivated", {
                templateUrl: "views/users/listUsers.html",
                controller: "userCtrl",
            })
            .when("/listUsersBlock", {
                templateUrl: "views/users/listUsers.html",
                controller: "userCtrl",
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

.controller("userCtrl", [
    "userFactory",
    "$scope",
    "$http",
    function(userFactory, $scope, $http) {
        $scope.users = [];
        $scope.user = {};
        $scope.titleContent = '';

        $scope.currentPage = 1;
        $scope.itemsPerPage = 5;

        var url = window.location.href;
        var arr = url.split("/");
        var resultUrl = arr[4];

        $scope.myForm = {
            description: "",
            files: [],
        };

        $scope.repaginate = function() {
            $scope.currentPage = 1;
        };

        console.log('URL: ' + resultUrl)

        if (resultUrl == "listUsers") {
            $scope.titleContent = "tất cả";
            userFactory.getUsers().then(
                (data) => {
                    $scope.users = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }

        if (resultUrl == "listUsersActivated") {
            $scope.titleContent = "đã kích hoạt";
            userFactory.getUsersActivated().then(
                (data) => {
                    $scope.users = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }

        if (resultUrl == "listUsersNotActivated") {
            $scope.titleContent = "chưa kích hoạt";
            userFactory.getUsersNotActivated().then(
                (data) => {
                    $scope.users = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }

        if (resultUrl == "listUsersBlock") {
            $scope.titleContent = "đã bị khóa";
            userFactory.getUsersBlock().then(
                (data) => {
                    $scope.users = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        }

        $scope.getUser = function(id) {
            userFactory.getUser(id).then(
                (data) => {
                    $scope.user = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        };

        $scope.update = function(id) {
            if ($scope.myForm.files[0] != null) {
                $scope.doUploadFile();
                $scope.user.picture = $scope.myForm.files[0].name;
            }

            // Convert Date
            console.log($scope.user.birthday);
            var date = new Date($scope.user.birthday),
                day = ("0" + (date.getDate() + 1)).slice(-2),
                mnth = ("0" + (date.getMonth() + 1)).slice(-2);
            $scope.user.birthday = [date.getFullYear(), mnth, day].join("-");
            console.log($scope.user.birthday);

            userFactory.updateUser(id, $scope.user).then(
                (data) => {
                    $scope.user = data;
                    alert("Update user successfully!");
                    // location.reload();
                },
                (reason) => {
                    alert("Update user failed!\nError: " + reason);
                }
            );
        };

        $scope.delete = function(id) {
            userFactory.deleteUser(id).then(
                (data) => {
                    $scope.user = data;
                    alert("Delete user successfully!");
                    location.reload();
                },
                (reason) => {
                    alert("Delete user failed!\nError: " + reason);
                    location.reload();
                }
            );
        };

        $scope.block = function(id) {
            userFactory.blockUser(id, $scope.user).then(
                (data) => {
                    $scope.user = data;
                    alert("Block user successfully!");
                    location.reload();
                },
                (reason) => {
                    alert("Block user failed!\nError: " + reason);
                    location.reload();
                }
            );
        };

        $scope.active = function(id) {
            console.log($scope.statusActive);
            userFactory.activeUser(id, $scope.user, $scope.statusActive).then(
                (data) => {
                    $scope.user = data;
                    alert("Active user successfully!");
                    location.reload();
                },
                (reason) => {
                    alert("Active user failed!\nError: " + reason);
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
        // SORT LIST USER
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