(function () {
    'use strict';

    angular.module('simpleCurrencyPredicator').controller('HomeCtrl', function ($scope, currencyFactory, dictionaryFactory) {
        var vm = this;

        vm.currencies = [];

        dictionaryFactory.query({name: 'currency'}).$promise.then(function (data) {
            vm.currencies = data;
        });

        vm.queryOptions = {
            table: 'a'
        };

        vm.datasetOverride = [
            {
                yAxisID: 'y-axis-1'
            }
        ];
        vm.options = {
            scales: {
                yAxes: [
                    {
                        id:       'y-axis-1',
                        type:     'linear',
                        display:  true,
                        position: 'left'
                    }
                ]
            }
        };

        vm.fetch = function (qo) {
            currencyFactory.get(qo).$promise.then(function (data) {
                vm.labels = [];
                vm.series = [data.code];
                vm.data = [[]];
                for (var i = 0; i < data.rates.length; i++) {
                    var rate = data.rates[i];
                    vm.labels.push(rate.effectiveDate);
                    vm.data[0].push(rate.mid);
                }
            });
        };

    });

})();