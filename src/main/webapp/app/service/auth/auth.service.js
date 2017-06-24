(function () {
    'use strict';

    angular.module('simpleCurrencyPredicator')
        .factory('Auth', function Auth(Principal, AuthenticateCli, localStorageService, $rootScope, $state, $q) {
            return {
                isToken: function () {
                    return !!localStorageService.get('token');
                },

                login: function (credentials) {
                    return this.loginMain(AuthenticateCli, credentials);
                },

                loginMain: function (cli, credentials) {
                    var deferred = $q.defer();

                    cli.login(credentials).$promise.then(function (data) {
                        localStorageService.set('token', data.token);
                        Principal.identity(true).then(function (identity) {
                            identity.passwordExpireDate = data.passwordExpireDate;
                            deferred.resolve(identity);
                        }).catch(function (err) {
                            deferred.reject(err);
                        });
                    }).catch(function (err) {
                        deferred.reject(err);
                    });

                    return deferred.promise;
                },

                logout: function () {
                    localStorageService.remove('token');
                    Principal.authenticate(null);
                },

                authorize: function () {
                    return Principal.identity().then(function () {
                        if (!$rootScope.toState.data) return;
                        var siteRoles = $rootScope.toState.data.roles || [];

                        if (siteRoles && siteRoles.length > 0 && !Principal.isInAnyRole(siteRoles)) {
                            $state.go('login');
                        }
                    });
                }
            };
        });

})();