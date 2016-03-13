(function () {
	var as = angular.module('angularspring');

	as.controller('EventMasterController', function($scope, $http, i18n, $rootScope) {    	
    	//alert("in EventMasterController");
    	$scope.stepsModel = [];
    	$scope.cityOption;
    	$scope.eventDate;
    	document.getElementById("homeHeader").style.display = 'none';

    	$scope.updateEventDate = function (date){
// $scope.eventDate = date;
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
    		alert("hi")
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