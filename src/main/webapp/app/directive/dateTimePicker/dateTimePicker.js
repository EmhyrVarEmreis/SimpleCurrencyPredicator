(function () {
    'use strict';

    angular.module('simpleCurrencyPredicator')
        .directive('dateTimePicker', function () {
            return {
                scope:       {
                    ngModel:     "=",
                    ngDisabled:  "=",
                    label:       "@",
                    name:        "@",
                    placeholder: "@",
                    format:      "@",
                    locale:      "@"
                },
                restrict:    'E',
                templateUrl: '/directive/dateTimePicker/dateTimePicker.html',
                link:        function ($scope, element) {
                    $scope.format = $scope.format || 'YYYY-MM-DD HH:mm:ss';
                    $scope.locale = $scope.locale || 'en';

                    var elementDiv = element.find('div');
                    elementDiv.datetimepicker({
                        format: $scope.format,
                        locale: $scope.locale
                    });

                    elementDiv.on('dp.change', function (event) {
                        $scope.$apply(function () {
                            $scope.ngModel = (event.date ? event.date.format($scope.format) : null);
                        });
                    });

                }
            };
        });

})();