(function () {
    'use strict';

    angular.module('simpleCurrencyPredicator').config(function ($stateProvider) {
        $stateProvider.state("home", {
            parent:  'restrictedSite',
            url:     "/home",
            views:   {
                'content@': {
                    templateUrl:  '/ui/content/home/home.html',
                    controller:   'HomeCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {}
        });
    });
})();