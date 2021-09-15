var myApp = angular.module('myApp');

myApp.directive("convertToNumber", function () {
  return {
    require: "ngModel",
    link: function (scope, element, attr, ngModel) {
      ngModel.$parsers.push(function (val) {
        return parseInt(val, 10);
      });
      ngModel.$formatters.push(function (val) {
        return "" + val;
      });
    },
  };
});
