(function () {
    'use strict';

    angular.module('simpleCurrencyPredicator')
        .factory('authInterceptor', function ($rootScope, $q, $location, localStorageService, $injector, $window) {
            return {
                request:       function (config) {
                    config.headers = config.headers || {};
                    var token = localStorageService.get('token');

                    if (!token || typeof(token) !== 'string') return config;
                    var expires = token.split(':')[1];

                    if (!expires) return config;
                    expires = parseInt(expires);

                    if (expires > new Date().getTime()) {
                        config.headers['x-auth-token'] = token;
                    }

                    return config;
                },
                response:      function (response) {
                    var token = response.headers('x-auth-token');
                    if (token) localStorageService.set('token', token);
                    return response;
                },
                responseError: function (rejection) {
                    if (rejection.status === 401 && !$rootScope.sessionLost) {
                        $rootScope.sessionLost = true;
                        if ($location.$$path !== '/login') {
                            $injector.get('$uibModal').open({
                                templateUrl: 'scripts/ui/common/dialogs/statusDialog/statusDialog.html',
                                controller:  'statusDialogCtrl',
                                resolve:     {
                                    conf: function () {
                                        return {
                                            modalTitle: 'Informacja',
                                            modalBody:  'Twoja sesja wygasła lub cofnięto dostęp do zasobu!<br/><strong>Zaloguj się ponownie!</strong>',
                                            modalMode:  'info',
                                            isOK:       false
                                        };
                                    }
                                }
                            }).result.then(function () {
                                $rootScope.sessionLost = false;
                            }, function () {
                                $rootScope.sessionLost = false;
                            });
                        }
                        $location.path('/login');
                    } else if (rejection.status === 403) {
                        $injector.get('$uibModal').open({
                            templateUrl: 'scripts/ui/common/dialogs/statusDialog/statusDialog.html',
                            controller:  'statusDialogCtrl',
                            resolve:     {
                                conf: function () {
                                    return {
                                        modalTitle: 'Ostrzeżenie',
                                        modalBody:  'Nie masz dostępu do tego zasobu!',
                                        modalMode:  'warning',
                                        isOK:       false
                                    };
                                }
                            }
                        });
                        $window.history.back();
                    }
                    return $q.reject(rejection);
                }
            };
        })

        .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
            $urlRouterProvider.otherwise(function ($injector) {
                if (!!$injector.get('localStorageService').get('token')) {
                    return '/home';
                } else {
                    return '/panel';
                }
            });
            $httpProvider.interceptors.push('authInterceptor');
        })

        .run(function ($transitions, $rootScope, $state, Auth, Principal) {
            $transitions.onStart({}, function ($transition$) {
                $rootScope.toState = $transition$.$to();

                if (Principal.isIdentityResolved()) {
                    Auth.authorize();
                }
            });
        });

})();
