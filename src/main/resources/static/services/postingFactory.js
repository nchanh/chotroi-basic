"use strict";

angular.module("myApp").factory("postingFactory", [
    "$http",
    function($http) {
        var postingFactory = {};
        var postings = [];
        var postingDetails = [];

        postingFactory.getPostings = function() {
            var promise = $http
                .get("/postings/find-all")
                .then((respone) => {
                    postings = respone.data;

                    return postings;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        postingFactory.updateStatusPosting = function(id, status, posting) {
            var promise = $http
                .put("/postings/" + id + "/set-" + status, posting)
                .then((respone) => {
                    posting = respone.data;
                    return posting;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        postingFactory.getPostingsUnapproved = function() {
            var promise = $http
                .get("/postings/list-unapproved")
                .then((respone) => {
                    postings = respone.data;
                    return postings;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        postingFactory.getPostingsApproved = function() {
            var promise = $http
                .get("/postings/list-approved")
                .then((respone) => {
                    postings = respone.data;
                    return postings;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        postingFactory.getPostingsSold = function() {
            var promise = $http
                .get("/postings/list-sold")
                .then((respone) => {
                    postings = respone.data;
                    return postings;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        postingFactory.getPostingsBlock = function() {
            var promise = $http
                .get("/postings/list-block")
                .then((respone) => {
                    postings = respone.data;
                    return postings;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        postingFactory.getPosting = function(id, posting) {
            var promise = $http
                .get("/postings/" + id + "/get", posting)
                .then((respone) => {
                    posting = respone.data;
                    return posting;
                })
                .catch((reason) => console.log(reason));
            return promise;
        };

        postingFactory.deletePosting = function(id) {
            var promise = $http.delete(
                "/postings/" + id + "/delete"
            );
            return promise;
        };

        return postingFactory;
    },
]);