(function () {
    'use strict';

    angular.module('simpleCurrencyPredicator').factory('userListFactory', function ($resource) {
        return $resource('api/user/list');
    });

    angular.module('simpleCurrencyPredicator').factory('userDetailsFactory', function ($resource) {
        return $resource('api/user/details');
    });

    angular.module('simpleCurrencyPredicator').factory('userProfileFactory', function ($resource) {
        return $resource('api/user/profile');
    });

})();
