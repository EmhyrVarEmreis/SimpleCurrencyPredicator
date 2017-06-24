(function () {
    'use strict';

    angular.module('simpleCurrencyPredicator').controller('LoginCtrl', function ($rootScope, $scope, $state, $timeout, Auth) {
        $scope.user = {};
        $scope.error = {};

        $scope.authenticationErrorStatusCode = false;
        $scope.authenticationErrorSuperStatusCode = false;


        $timeout(function () {
            angular.element('[ng-model="username"]').focus();
        });

        $scope.clearError = function () {
            $scope.error = {};
        };

        $scope.createError = function (error, mode) {
            $scope.clearError();
            if (!!mode) {
                $scope.error.mode = mode;
            } else {
                $scope.error.mode = 'std';
            }
            $scope.error.error = true;
            $scope.error.code = error.status;
            $scope.error.data = error.data;
        };

        $scope.login = function () {
            Auth.logout();
            $scope.clearError();
            Auth.login({
                login:    $scope.username,
                password: $scope.password
            }).then(function () {
                $state.go('home');
            }).catch(function (error) {
                $scope.createError(error);
            });
        };

    });

})();