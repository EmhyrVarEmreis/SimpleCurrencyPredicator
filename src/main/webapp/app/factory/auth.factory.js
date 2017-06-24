(function () {
    'use strict';

    angular.module('simpleCurrencyPredicator')
        .factory('AccountCli', function ($resource) {
            return $resource('api/account', {}, {
                'get': {
                    method:      'GET', params: {}, isArray: false,
                    interceptor: {
                        response: function (response) {
                            // expose response
                            return response;
                        }
                    }
                }
            });
        })
        .factory('AuthenticateCli', function ($resource) {
            return $resource('api/authenticate', {}, {
                'login': {method: 'POST'}
            });
        });

})();
