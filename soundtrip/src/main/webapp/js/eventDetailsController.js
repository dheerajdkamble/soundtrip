(function () {
    
	var as = angular.module('angularspring');

as.controller('EventDetailsController', function ($scope, $http, i18n) {
	
	var actionUrl = 'action/eventdetails/';
	
	loadEventDetails = function() {
		var url = window.location.href;
		var id = url.substring(url.lastIndexOf("/")+1, url.indexOf("-"));

		$http.get(actionUrl + id).success(function (data) {
				$scope.eventDetail = data;
		});
	};
	loadEventDetails();
	
	// geolocation
	loadGeolocationCity = function() {
		console.log("Inside load geolocation city:::");
		$scope.$parent.selectedHomeCity = "All Cities";
		/* Geolocation code starts */
        
    	var geocoder = new google.maps.Geocoder();
    	if (navigator.geolocation) {
    		navigator.geolocation
    				.getCurrentPosition(successFunction, errorFunction);
    	}
    	// Get the latitude and the longitude;
    	function successFunction(position) {
    		console.log("Inside load geolocation city successfunction:::");
    		var lat = position.coords.latitude;
    		var lng = position.coords.longitude;
    		console.log("latitude : " + lat);
    		console.log("longitude : " + lng);
    		codeLatLng(lat, lng)
    	}

    	function errorFunction() {
    		console.log("Inside load geolocation city errorFunction:::");
    	}

    	function codeLatLng(lat, lng) {
    		console.log("Inside load geolocation city codeLatLng:::");
    		$scope.$parent.selectedHomeCity = "All Cities";
    		$scope.$parent.selectedHomeCityId = undefined;
    		var latlng = new google.maps.LatLng(lat, lng);
    		geocoder
    				.geocode(
    						{
    							'latLng' : latlng
    						},
    						function(results, status) {
    							/* alert("status : " + status); */
    							console.log("Inside load geolocation city status : " + status);
    							if (status == google.maps.GeocoderStatus.OK) {
//    								 alert("city : " +  results[0].address_components[4].short_name);
    								
    								if(results[0].address_components[4].short_name.indexOf("Thane") > -1 || results[0].address_components[4].short_name.indexOf("Mumbai") > -1 ||
    								   results[0].address_components[5].short_name.indexOf("Thane") > -1 || results[0].address_components[5].short_name.indexOf("Mumbai") > -1 ||
    								   results[0].address_components[6].short_name.indexOf("Thane") > -1 || results[0].address_components[6].short_name.indexOf("Mumbai") > -1){
    									console.log("city Thane or Mumbai::::: ");
    									$scope.$parent.selectedHomeCity = 'Mumbai';
    									$scope.$parent.selectedHomeCityId = '1';
    									$scope.$apply();
    									
    									
    								}else if (results[0].address_components[4].short_name.indexOf("Pune") > -1) {
    									console.log("pune::::: ");
    									$scope.$parent.selectedHomeCity = 'Pune';
    									$scope.$parent.selectedHomeCityId = '2';
    									$scope.$apply();
    									
//    									$scope.allEventsListHome = [];
//    					        		for (var i = 0; i < $scope.allEventsListHomeBackup.length; i++) {
//    										if($scope.allEventsListHomeBackup[i].city.name == $scope.$parent.selectedHomeCity){
//    											$scope.allEventsListHome.push($scope.allEventsListHomeBackup[i]);
//    										}
//    									}
									}
    								else{
    									console.log("no city setting All Cities ::::: ");
    									$scope.$parent.selectedHomeCity = "All Cities";
    									$scope.$parent.selectedHomeCityId = undefined;
    									//$scope.allEventsListHome = $scope.allEventsListHomeBackup;
    									$scope.$apply();
    									
    									
    								}
    								if (results[1]) {
    									for (var i = 0; i < results[0].address_components.length; i++) {
    										for (var b = 0; b < results[0].address_components[i].types.length; b++) {
    											if (results[0].address_components[i].types[b] == "locality") {
    												city = results[0].address_components[i];
    												break;
    											}
    										}
    									}
    									return city.long_name;
    								} else {
    									$scope.$parent.selectedHomeCity = "All Cities";
    									$scope.$parent.selectedHomeCityId = undefined;
    									$scope.$apply();
    									
    								}
    							} else {
    								$scope.$parent.selectedHomeCity = "All Cities";
									$scope.$parent.selectedHomeCityId = undefined;
									$scope.$apply();
									
    							}
    						});
    	}
    /* Geolocation code ends */
    		
	};
	//loadGeolocationCity();
	if ($scope.$parent.selectedHomeCity == undefined ) {
		loadGeolocationCity();
	}
});
}());