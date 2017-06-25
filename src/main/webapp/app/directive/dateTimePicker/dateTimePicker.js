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
                    locale:      "@",
                    minDate:     "@",
                    maxDate:     "@"
                },
                restrict:    'E',
                templateUrl: '/directive/dateTimePicker/dateTimePicker.html',
                link:        function ($scope, element) {
                    $scope.format = $scope.format || 'YYYY-MM-DD HH:mm:ss';
                    $scope.locale = $scope.locale || 'en';

                    var elementDiv = element.find('div');

                    var options = {
                        format: $scope.format,
                        locale: $scope.locale
                    };

                    if ($scope.minDate) {
                        if ($scope.minDate === 'now') {
                            options.minDate = moment();
                        } else {
                            options.minDate = moment($scope.minDate);
                        }
                    }
                    if ($scope.maxDate) {
                        if ($scope.maxDate === 'now') {
                            options.maxDate = moment();
                        } else {
                            options.maxDate = moment($scope.maxDate);
                        }
                    }

                    elementDiv.datetimepicker(options);

                    elementDiv.on('dp.change', function (event) {
                        $scope.$apply(function () {
                            $scope.ngModel = (event.date ? event.date.format($scope.format) : null);
                        });
                    });

                }
            };
        });

})();