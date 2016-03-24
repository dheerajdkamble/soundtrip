(function () {
    var as = angular.module('angularspring');

    as.controller('MainController', function ($scope, $rootScope, $http, i18n, $location) {
    	$scope.selectedHomeCity;
    	$scope.selectedHomeCityId;
    	$scope.cityOptions = [{"id":"1","name":"Mumbai"},{"id":"2","name":"Pune"}];
    	$scope.genrelist = ["Pop","Jazz","Blues","Classic Rock","Indian Classical"];
    	$scope.selectedGenre = '';
    	
    	$scope.getSelectedGenre = function(){
    		console.log("check selected genre:::"+$scope.selectedGenre);
    	}
    	
    	getCityOptions = function() {
    		alert('calling getcityOptions');
    		$http.get("action/getallcities").success(function (data) {
        		$scope.cityOptions = data;
        		console.log("check city json:::"+JSON.stringify($scope.cityOptions,null,4));
            });
    	};
    	
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
        
        $scope.citySelected = function() {
        	$rootScope.$broadcast('eventListFiltered');
    	};
    });

    as.controller('AdminController', function ($scope, $http) {
        $http.get('action/user');
    });
}());