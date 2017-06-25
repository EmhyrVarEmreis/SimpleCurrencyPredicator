(function () {
    'use strict';

    angular.module('simpleCurrencyPredicator').factory('dictionaryFactory', function ($resource) {
        return $resource('/api/dictionary');
    });

})();
