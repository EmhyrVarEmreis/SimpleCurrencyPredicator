(function () {
    'use strict';

    angular.module('simpleCurrencyPredicator')
        .config(function stateConfig($stateProvider, $urlRouterProvider) {

                $stateProvider.state("publicSite", {
                    abstract: true,
                    views:    {
                        'footer@': {
                            templateUrl:  '/ui/footer/footer.html',
                            controller:   'FooterCtrl',
                            controllerAs: 'ftr'
                        }
                    }
                });

                $stateProvider.state("restrictedSite", {
                    abstract: true,
                    parent:   'publicSite',
                    data:     {
                        roles: ['authenticated']
                    },
                    resolve:  {
                        authorize: function (Auth) {
                            return Auth.authorize();
                        }
                    },
                    views:    {
                        'header@': {
                            templateUrl:  '/ui/header/header.html',
                            controller:   'HeaderCtrl',
                            controllerAs: 'hdr'
                        }
                    }
                });

                $urlRouterProvider.otherwise('/home');

            }
        );

})();
