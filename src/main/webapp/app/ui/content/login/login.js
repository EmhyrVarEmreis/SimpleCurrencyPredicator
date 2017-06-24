(function () {
    'use strict';

    angular.module('simpleCurrencyPredicator').config(function ($stateProvider) {
        $stateProvider.state("login", {
            parent:  'publicSite',
            url:     "/login",
            views:   {
                'content@': {
                    templateUrl: '/ui/content/login/login.html',
                    controller:  'LoginCtrl'
                }
            },
            resolve: {}
        });
    });

})();