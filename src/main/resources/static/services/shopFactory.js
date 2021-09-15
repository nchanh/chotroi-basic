"use strict";

angular.module("myApp").factory("shopFactory", [
    "$http",
    function($http) {
        var shopFactory = {};
        var shops = [];

        shopFactory.getShops = function() {
            var promise = $http
                .get("/shops/find-all")
                .then((respone) => {
                    shops = respone.data;

                    return shops;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        shopFactory.getShopsActivated = function() {
            var promise = $http
                .get("/shops/list-activated")
                .then((respone) => {
                    shops = respone.data;
                    return shops;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        shopFactory.getShopsNotActivated = function() {
            var promise = $http
                .get("/shops/list-not-activated")
                .then((respone) => {
                    shops = respone.data;

                    return shops;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        shopFactory.getShopsBlock = function() {
            var promise = $http
                .get("/shops/list-block")
                .then((respone) => {
                    shops = respone.data;

                    return shops;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        shopFactory.getShop = function(id, shop) {
            var promise = $http
                .get("/shops/" + id + "/get", shop)
                .then((respone) => {
                    shop = respone.data;
                    return shop;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        shopFactory.updateShop = function(id, shop) {
            var promise = $http
                .put("/shops/" + id + "/update", shop)
                .then((respone) => {
                    shop = respone.data;
                    return shop;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        shopFactory.deleteShop = function(id) {
            var promise = $http.delete(
                "/shops/" + id + "/delete"
            );
            return promise;
        };

        shopFactory.blockShop = function(id, shop) {
            var promise = $http
                .put("/shops/" + id + "/block", shop)
                .then((respone) => {
                    shop = respone.data;
                    return shop;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        shopFactory.activeShop = function(id, shop, status) {
            var promise = $http
                .put("/shops/" + id + "/" + status, shop)
                .then((respone) => {
                    shop = respone.data;
                    return shop;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        return shopFactory;
    },
]);