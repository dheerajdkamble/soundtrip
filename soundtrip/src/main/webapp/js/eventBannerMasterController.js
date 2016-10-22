(function () {
    
		var as = angular.module('angularspring');
	
		as.controller('EventBannerMasterController', function ($scope, $http, i18n, $rootScope) {
	    var actionUrl = 'action/eventbannermaster/';
    	$scope.stepsModel = [];
    	$scope.indexOptions = ["1","2","3","4","5","6","7","8","9","10"];

    	var actionUrlEvents = 'action/eventbannermaster/',
        loadEventBanners = function () {
            $http.get(actionUrlEvents).success(function (data) {
                $scope.eventbannermasters = data;
            });
        };
        loadEventBanners();
    	
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
    	
    	$scope.delete = function (eventbannermaster) {
	        $http.delete(actionUrlEvents + eventbannermaster.id).success(function () {
	        	loadEventBanners();    	
	        });
	    };
    	
	    $scope.save = function () {
	    	console.log("Before saving ::: " + JSON.stringify($scope.newbannermaster, null, 4));
	    	if($scope.stepsModel[0] != null) {
	    		$scope.newbannermaster['image'] =  $scope.stepsModel[0];
	    	}
	    	
	    	console.log('before saving new banner master' + JSON.stringify($scope.newbannermaster, null, 4));
	        $http.post(actionUrl, $scope.newbannermaster).success(function () {
	        	loadEventBanners();
	        	$scope.newbannermaster = {};
	        	$scope.stepsModel = [];
	        });
	    };
    
	    $scope.edit = function (index) {
	        $scope.newbannermaster = angular.copy($scope.eventbannermasters[index]);
	        var img = new Image();
	        img.src = "http://localhost:8080/project/images/" + $scope.newbannermaster.image;
//	        img.src = "http://103.35.123.84:8080/project/images/" + $scope.neweventmaster.image;
	        $scope.stepsModel[0]= img.src;
	    };
	    
    });
}());