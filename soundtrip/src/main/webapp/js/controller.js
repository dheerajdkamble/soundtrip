(function () {
    var as = angular.module('angularspring');

    as.controller('MainController', function ($scope, $rootScope, $http, i18n, $location) {
    	
        $scope.language = function () {
            return i18n.language;
        };
        $scope.setLanguage = function (lang) {
            i18n.setLanguage(lang);
        };
        $scope.activeWhen = function (value) {
            return value ? 'active' : '';
        };

        $scope.path = function () {
            return $location.url();
        };

        $scope.login = function () {
            $scope.$emit('event:loginRequest', $scope.username, $scope.password);
            $('#login').modal('hide');
        };
        $scope.logout = function () {
            $rootScope.user = null;
            $scope.username = $scope.password = null;
            $scope.$emit('event:logoutRequest');
            $location.url('/person');
        };

    });

    as.controller('PersonController', function ($scope, $http, i18n) {
        var actionUrl = 'action/person/',
            load = function () {
                $http.get(actionUrl).success(function (data) {
                    $scope.persons = data;
                });
            };

        load();

        $scope.delete = function (person) {
            $http.delete(actionUrl + person.id).success(function () {
                load();
            });
        };

        $scope.save = function () {
            $http.post(actionUrl, $scope.person).success(function () {
                load();
            });
        };

        $scope.order = '+firstName';

        $scope.orderBy = function (property) {
            $scope.order = ($scope.order[0] === '+' ? '-' : '+') + property;
        };

        $scope.orderIcon = function (property) {
            return property === $scope.order.substring(1) ? $scope.order[0] === '+' ? 'icon-chevron-up' : 'icon-chevron-down' : '';
        };
    });

    as.controller('AdminController', function ($scope, $http) {
        $http.get('action/user');
    });
    
    as.controller('EventMasterController', function($scope, $http, i18n) {
    	var actionUrlEvents = 'action/eventmaster/',
        loadEvents = function () {
            $http.get(actionUrlEvents).success(function (data) {
                $scope.eventmasters = data;
                console.log("eventsmaster >>> " + $scope.eventmasters);
            });
        };

        loadEvents();

	    $scope.delete = function (eventmaster) {
	        $http.delete(actionUrlEvents + eventmaster.id).success(function () {
	        	loadEvents();
	        });
	    };
	
	    $scope.edit = function (index) {
	        $scope.neweventmaster = angular.copy($scope.eventmasters[index]);
	    };
	    
	    $scope.save = function () {
	        $http.post(actionUrlEvents, $scope.neweventmaster).success(function () {
	        	loadEvents();
	        	$scope.neweventmaster = {};
	        });
	    };
	
	    $scope.order = '+name';
	
	    $scope.order = function (property) {
	        $scope.order = ($scope.order[0] === '+' ? '-' : '+') + property;
	    };
	
	    $scope.orderIcon = function (property) {
	        return property === $scope.order.substring(1) ? $scope.order[0] === '+' ? 'icon-chevron-up' : 'icon-chevron-down' : '';
	    };
    });
}());