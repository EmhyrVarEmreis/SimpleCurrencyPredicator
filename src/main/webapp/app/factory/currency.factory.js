(function () {
    'use strict';

    angular.module('simpleCurrencyPredicator').factory('currencyFactory', function ($resource) {
        return $resource('/api/currency/history/:table/:code/:startDate/:endDate/?format=json');
    });

})();
