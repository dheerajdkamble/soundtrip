(function () {
    
	var as = angular.module('angularspring');

as.controller('EventHomeController', function ($scope, $http, i18n) {

    	var actionUrl = 'action/eventhome/';
    	var actionUrlEventList = 'action/eventlisthome/';
    	document.getElementById("homeHeader").style.display = 'block';
    	
    	loadGeolocationCity = function() {
    		/* Geolocation code starts */
            
        	var geocoder = new google.maps.Geocoder();
        	if (navigator.geolocation) {
        		navigator.geolocation
        				.getCurrentPosition(successFunction, errorFunction);
        	}
        	//Get the latitude and the longitude;
        	function successFunction(position) {
        		var lat = position.coords.latitude;
        		var lng = position.coords.longitude;
        		codeLatLng(lat, lng)
        	}

        	function errorFunction() {
        		/* alert("Geocoder failed"); */
        		//TODO : here you need to select all cities as default
        	}

        	function codeLatLng(lat, lng) {

        		var latlng = new google.maps.LatLng(lat, lng);
        		geocoder
        				.geocode(
        						{
        							'latLng' : latlng
        						},
        						function(results, status) {
        							/*alert("status : " + status);*/
        							if (status == google.maps.GeocoderStatus.OK) {
        								console.log("view geo json:::"+JSON.stringify(results,null,4))
        								//alert("city : " + results[0].address_components[2].short_name);
        								
        								if(results[0].address_components[4].short_name.indexOf("Thane") > -1 || results[0].address_components[4].short_name.indexOf("Mumbai") > -1){
        									$scope.$parent.selectedHomeCity = 'mumbai';
        									$scope.$parent.selectedHomeCityId = '1';
        									//$scope.$parent.selectedHomeCity = undefined;
        									//$scope.$parent.selectedHomeCityId = undefined;
        									loadAllEvents();
        								}else if (results[0].address_components[4].short_name.indexOf("Pune") > -1) {
        									$scope.$parent.selectedHomeCity = 'pune';
        									$scope.$parent.selectedHomeCityId = '2';
        									loadAllEvents();
										}
        								else{
        									$scope.$parent.selectedHomeCity = undefined;
        									$scope.$parent.selectedHomeCityId = undefined;
        									loadAllEvents();
        								}
        								if (results[1]) {
        									for (var i = 0; i < results[0].address_components.length; i++) {
        										for (var b = 0; b < results[0].address_components[i].types.length; b++) {

        											//there are different types that might hold a city admin_area_lvl_1 usually does in come cases looking for sublocality type will be more appropriate
        											if (results[0].address_components[i].types[b] == "locality") {
        												//this is the object you are looking for
        												city = results[0].address_components[i];
        												break;
        											}
        										}
        									}
        									return city.long_name;

        								} else {
        									//TODO : here you need to select all cities as default
        								}
        							} else {
        								//TODO : here you need to select all cities as default
        							}
        						});
        	}
        /* Geolocation code ends */
        		
    	};
    	loadGeolocationCity();
    	
        loadRollingImages = function () {
        	$http.get(actionUrl).success(function (data) {
        		$scope.eventRollingImages = data;
            });
        };
        loadRollingImages();
        
        loadAllEvents = function () {
        	$http.get(actionUrlEventList).success(function (data) {
        		$scope.allEventsListHomeBackup = data;
        		$scope.allEventsListHome = [];
        		//console.log("here to check json::::"+JSON.stringify($scope.allEventsListHomeBackup,null,4));
        		//alert("check::"+$scope.$parent.selectedHomeCityId)
        		for (var i = 0; i < $scope.allEventsListHomeBackup.length; i++) {
        			//console.log("check here in for:::"+$scope.allEventsListHomeBackup[i].city.id +"and"+$scope.$parent.selectedHomeCityId);
					if($scope.allEventsListHomeBackup[i].city.id == $scope.$parent.selectedHomeCityId){
						//alert("got")
						$scope.allEventsListHome.push($scope.allEventsListHomeBackup[i]);
					}
				}
        		if($scope.allEventsListHome.length < 1){
        			$scope.allEventsListHome = data;
        		}
            });
        };
        
        
    	
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

        /*
		 * $scope.order = '+firstName';
		 * 
		 * $scope.orderBy = function (property) { $scope.order =
		 * ($scope.order[0] === '+' ? '-' : '+') + property; };
		 * 
		 * $scope.orderIcon = function (property) { return property ===
		 * $scope.order.substring(1) ? $scope.order[0] === '+' ?
		 * 'icon-chevron-up' : 'icon-chevron-down' : ''; };
		 */
    });
}());