(function () {
	var as = angular.module('angularspring');

	as.controller('EventMasterController', function($scope, $http, i18n, $rootScope) {    	
	    
    	$scope.stepsModel = [];
    	$scope.cityOption;
    	$scope.genreOption;
    	$scope.genreOptions = [];
    	$scope.eventDate;
    	$scope.datetimepickerval='';
    	document.getElementById("homeHeader").style.display = 'none';

    	$scope.updateEventDate = function (date){
    		// $scope.eventDate = date;
    	};
    	
    	getGenreOptions = function() {
       		$scope.genreOptions = $scope.$parent.genrelist;
    	};
    	getGenreOptions();
    	
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
	    	if($scope.stepsModel[0] != null) {
	    		$scope.neweventmaster['image'] =  $scope.stepsModel[0];
	    	}
	    	$scope.neweventmaster.datetime = +$scope.datetimepickerval.getFullYear()+"-"+($scope.datetimepickerval.getMonth()+1)+"-"+$scope.datetimepickerval.getDate()+" "+$scope.datetimepickerval.getHours()+":"+$scope.datetimepickerval.getMinutes()+":"+$scope.datetimepickerval.getSeconds();
	        $http.post(actionUrlEvents, $scope.neweventmaster).success(function () {
	        	loadEvents();
	        	$scope.neweventmaster = {};
	        	$scope.eventDate = '';
	        	$scope.cityOption = '';
	        	$scope.genreOption = '';
	        	$scope.stepsModel = [];
	        });
	    };
	
	    $scope.citySelectedForEventMaster = function() {
	    	$scope.neweventmaster.city = $scope.cityOption;
	    };
	    
	    $scope.genreSelectedForEventMaster = function() {
	    	$scope.neweventmaster.genre = $scope.genreOption;
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