(function () {
    var as = angular.module('angularspring');

    as.controller('MainController', function ($scope, $rootScope, $http, i18n, $location) {
    	$scope.selectedHomeCity;
    	$scope.selectedHomeCityId;
    	$scope.cityOptions = [{"id":"1","name":"mumbai"},{"id":"2","name":"pune"}];

    	getCityOptions = function() {
    		alert('calling getcityOptions');
    		$http.get("action/getallcities").success(function (data) {
        		$scope.cityOptions = data;
        		console.log("check city json:::"+JSON.stringify($scope.cityOptions,null,4));
            });
    	};
    	//getCityOptions();
    	
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
    		
    	}
    });


    as.controller('AdminController', function ($scope, $http) {
        $http.get('action/user');
    });
    
    as.controller('EventMasterController', function($scope, $http, i18n, $rootScope) {    	
    
    	$scope.stepsModel = [];
    	$scope.cityOption;
    	$scope.eventDate;
    	document.getElementById("homeHeader").style.display = 'none';

    	$scope.updateEventDate = function (date){
//    		  $scope.eventDate = date;
    	};
    	
    	$scope.imageUpload = function(element){
    	    var reader = new FileReader();
    	    reader.onload = $scope.imageIsLoaded;
    	    reader.readAsDataURL(element.files[0]);
    	}

    	$scope.imageIsLoaded = function(e){
    	    $scope.$apply(function() {
    	    	$scope.stepsModel = [];
    	        $scope.stepsModel.push(e.target.result);
    	    });
    	}
    		
    	var actionUrlEvents = 'action/eventmaster/',
        loadEvents = function () {
            $http.get(actionUrlEvents).success(function (data) {
                $scope.eventmasters = data;
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
	    	console.log("event date : " + $scope.neweventmaster.date);
	    	if($scope.stepsModel[0] != null) {
	    		$scope.neweventmaster['image'] =  $scope.stepsModel[0];
	    	}
	        $http.post(actionUrlEvents, $scope.neweventmaster).success(function () {
	        	loadEvents();
	        	$scope.neweventmaster = {};
	        	$scope.eventDate = '';
	        });
	    };
	
	    $scope.citySelectedForEventMaster = function() {
	    	$scope.neweventmaster.city = $scope.cityOption;
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