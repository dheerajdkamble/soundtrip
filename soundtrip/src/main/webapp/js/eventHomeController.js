(function () {
    
	var as = angular.module('angularspring');

as.controller('EventHomeController', function ($scope, $http, i18n) {
	
    	var actionUrl = 'action/eventhome/';
    	var actionUrlEventList = 'action/eventlisthome/';
    	
    	document.getElementById("homeHeader").style.display = 'block';

    	loadAllEvents = function () {
        	$http.get(actionUrlEventList).success(function (data) {
        		$scope.allEventsListHomeBackup = data;
        		$scope.allEventsListHome = data;
            });
        };
        loadAllEvents();
    	
    	loadGeolocationCity = function() {
    		/* Geolocation code starts */
            
        	var geocoder = new google.maps.Geocoder();
        	if (navigator.geolocation) {
        		navigator.geolocation
        				.getCurrentPosition(successFunction, errorFunction);
        	}
        	// Get the latitude and the longitude;
        	function successFunction(position) {
        		var lat = position.coords.latitude;
        		var lng = position.coords.longitude;
        		codeLatLng(lat, lng)
        	}

        	function errorFunction() {
        		alert("Geocoder failed");
        		// TODO : here you need to select all cities as default
        	}

        	function codeLatLng(lat, lng) {
        		var latlng = new google.maps.LatLng(lat, lng);
        		geocoder
        				.geocode(
        						{
        							'latLng' : latlng
        						},
        						function(results, status) {
        							/* alert("status : " + status); */
        							if (status == google.maps.GeocoderStatus.OK) {
        								console.log("view geo json:::"+JSON.stringify(results,null,4))
        								 alert("city : " +  results[0].address_components[4].short_name);
        								
        								if(results[0].address_components[4].short_name.indexOf("Thane") > -1 || results[0].address_components[4].short_name.indexOf("Mumbai") > -1 ||
        								   results[0].address_components[5].short_name.indexOf("Thane") > -1 || results[0].address_components[5].short_name.indexOf("Mumbai") > -1 ||
        								   results[0].address_components[6].short_name.indexOf("Thane") > -1 || results[0].address_components[6].short_name.indexOf("Mumbai") > -1){
        									console.log("city Thane or Mumbai::::: " + $scope.allEventsListHomeBackup.length);
        									$scope.$parent.selectedHomeCity = 'Mumbai';
        									$scope.$parent.selectedHomeCityId = '1';
        									$scope.allEventsListHome = [];
        					        		for (var i = 0; i < $scope.allEventsListHomeBackup.length; i++) {
        										if($scope.allEventsListHomeBackup[i].city.name == $scope.$parent.selectedHomeCity){
        											console.log("adding item to home list");
        											$scope.allEventsListHome.push($scope.allEventsListHomeBackup[i]);
        										}
        									}
        								}else if (results[0].address_components[4].short_name.indexOf("Pune") > -1) {
        									$scope.$parent.selectedHomeCity = 'Pune';
        									$scope.$parent.selectedHomeCityId = '2';
        									$scope.allEventsListHome = [];
        					        		for (var i = 0; i < $scope.allEventsListHomeBackup.length; i++) {
        										if($scope.allEventsListHomeBackup[i].city.name == $scope.$parent.selectedHomeCity){
        											$scope.allEventsListHome.push($scope.allEventsListHomeBackup[i]);
        										}
        									}
										}
        								else{
        									
        									$scope.$parent.selectedHomeCity = undefined;
        									$scope.$parent.selectedHomeCityId = undefined;
        									$scope.allEventsListHome = $scope.allEventsListHomeBackup;
        									
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
        									
        								}
        							} else {
        								
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
        
        $scope.$on('eventListFiltered', function() {
        	console.log("chk size:::"+$scope.allEventsListHomeBackup.length);
        	$scope.allEventsListHome = [];
        	for (var i = 0; i < $scope.allEventsListHomeBackup.length; i++) {
				if($scope.allEventsListHomeBackup[i].city.name == $scope.$parent.selectedHomeCity){
					$scope.allEventsListHome.push($scope.allEventsListHomeBackup[i]);
				}
			}
        	if($scope.allEventsListHome.length === 0) {
        		$scope.allEventsListHome = $scope.allEventsListHomeBackup;
        	}
        });
        
        $scope.$on('eventListSearch', function() {
        	var stringToSearch = $scope.$parent.selectedGenre.toLowerCase();
        	$scope.allEventsListHome = [];
        	var searchStrings = stringToSearch.split(" ");
        	var indexArr = [];
        	for (var index in $scope.allEventsListHomeBackup) {
        		for(var indexVar in searchStrings) {
        		   if(stringMatching($scope.allEventsListHomeBackup[index], searchStrings[indexVar])) {
        			   if(indexArr.indexOf(index) > -1) {
        				   break;
        			   }
        			   indexArr.push(index);
        		   }
        		}
        	}
        	
        	for(var indx in indexArr) {
        		$scope.allEventsListHome.push($scope.allEventsListHomeBackup[indexArr[indx]]);
        	}
        	
        	if($scope.allEventsListHome.length === 0) {
        		$scope.allEventsListHome = $scope.allEventsListHomeBackup;
        	}
        });
        
        function stringMatching(obj, val) {
        	var found = false;
            for (var i in obj) {
                if (!obj.hasOwnProperty(i)) continue;
                var property = obj[i];
                if (typeof property == 'object') {
                	if(stringMatching(obj[i], val)) {
                		return true;
                	}
                } else if ((property.toString()).toLowerCase().indexOf(val) > -1) {
                    return true;
                }
            }
            return found;
        }
    });
}());