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

    as.controller('EventHomeController', function ($scope, $http, i18n) {
    	var actionUrl = 'action/eventhome/';
    	
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
        								console.log("results :: " + JSON.stringify(results, 4, null));
        								/*alert("city : " + results[0].address_components[2].short_name);*/
        								
        								
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
        									alert("City long name : " + city.long_name + " short name : " + city.short_name);
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
        		console.log("rollingImages :: " + JSON.stringify($scope.eventRollingImages, 4, null));
            });
        };
        loadRollingImages();

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

    as.controller('AdminController', function ($scope, $http) {
        $http.get('action/user');
    });
    
    as.controller('EventMasterController', function($scope, $http, i18n, $rootScope) {
    	$scope.stepsModel = [];

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