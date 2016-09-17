(function () {
	var as = angular.module('angularspring');

	as.controller('EventMasterController', function($scope, $http, i18n, $rootScope) {    	
	    
    	$scope.stepsModel = [];
    	$scope.cityOption;
    	$scope.genreOption;
    	$scope.genreOptions = [];
    	$scope.eventDate;
    	$scope.datetimepickerval='';
    	$scope.neweventmasterfbrecord = {};

    	$scope.updateEventDate = function (date){
    		// $scope.eventDate = date;
    	};
    	
    	getGenreOptions = function() {
       		$scope.genreOptions = $scope.$parent.genrelist;
    	};
    	getGenreOptions();
    	
    	$scope.imageUpload = function(element){
    		console.log('inside image uploader');
    	    var reader = new FileReader();
    	    reader.onload = $scope.imageIsLoaded;
    	    reader.readAsDataURL(element.files[0]);
    	}

    	$scope.imageIsLoaded = function(e){
    		console.log('inside imageIsLoaded');
    	    $scope.$apply(function() {
    	    	console.log('inside apply');
    	    	$scope.stepsModel = [];
    	        $scope.stepsModel.push(e.target.result);
    	        console.log('stepsModel' + $scope.stepsModel);
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
	        $scope.neweventmasterfbrecord = $scope.neweventmaster; 
	        for(var i = 0 ; i < $scope.cityOptions.length; i++) {
	        	if($scope.cityOptions[i].id == $scope.neweventmaster.city.id) {
	        		$scope.cityOption = $scope.cityOptions[i];
	        		break;
	        	}
	        }
	        for(var i = 0 ; i < $scope.genreOptions.length; i++) {
	        	if($scope.genreOptions[i].name == $scope.neweventmaster.genre) {
	        		console.log("$scope.genreOptions[i] " + JSON.stringify($scope.genreOptions[i], null, 4));
	        		$scope.genreOption = $scope.genreOptions[i].name;
	        		break;
	        	}
	        }
	        var img = new Image();
//	        img.src = "http://localhost:8080/project/images/" + $scope.neweventmaster.image;
	        img.src = "http://103.35.123.84:8080/project/images/" + $scope.neweventmaster.image;
	        $scope.stepsModel[0]= img.src;
	    };
	    
	    $scope.save = function () {
	    	console.log("Before saving ::: " + JSON.stringify($scope.neweventmaster, null, 4));
	    	if($scope.stepsModel[0] != null) {
	    		$scope.neweventmaster['image'] =  $scope.stepsModel[0];
	    	}
	    	
	    	if(($scope.neweventmaster.datetime == null && $scope.neweventmaster.datetime == "") || ($scope.datetimepickerval != null && $scope.datetimepickerval != "")) {
	    		$scope.neweventmaster.datetime = +$scope.datetimepickerval.getFullYear()+"-"+($scope.datetimepickerval.getMonth()+1)+"-"+$scope.datetimepickerval.getDate()+" "+$scope.datetimepickerval.getHours()+":"+$scope.datetimepickerval.getMinutes()+":"+$scope.datetimepickerval.getSeconds();
	    	}
	    	console.log('before saving new event master' + JSON.stringify($scope.neweventmaster, null, 4));
	        $http.post(actionUrlEvents, $scope.neweventmaster).success(function () {
	        	loadEvents();
	        	$scope.neweventmasterfbrecord = $scope.neweventmaster;
	        	$scope.neweventmaster = {};
	        	$scope.eventDate = '';
	        	$scope.cityOption = '';
	        	$scope.genreOption = '';
	        	$scope.stepsModel = [];
	        });
	    };
	    
	    $scope.createFBEvent = function () {
	    	console.log("Before saving ::: " + JSON.stringify($scope.neweventmasterfbrecord, null, 4));
	        $http.post("action/eventmaster/fbEvent/", $scope.neweventmasterfbrecord).success(function () {
	        	$scope.neweventmasterfbrecord = {};
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