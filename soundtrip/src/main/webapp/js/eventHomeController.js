(function () {
    
	var as = angular.module('angularspring');

as.controller('EventHomeController', function ($scope, $http, i18n) {
	
    	var actionUrl = 'action/eventhome/';
    	var actionUrlEventList = 'action/eventlisthome/';
    	$scope.allEventsListHome = [];
    	$scope.allEventsListHomeBackup = [];
    	$scope.showMoreButtonVisible = false;
    	$scope.listEventScrollCount = 1;
    	//document.getElementById("homeHeader").style.display = 'block';
    	
    	

    	loadAllEvents = function () {
        	$http.get(actionUrlEventList).success(function (data) {
        		$scope.allEventsListHomeBackup = data;
        		//$scope.allEventsListHome = data;
        		$scope.allEventsListHome = [];
        		for (var i = 0; i < $scope.allEventsListHomeBackup.length; i++) {
					if($scope.allEventsListHomeBackup[i].city.name == $scope.$parent.selectedHomeCity){
						console.log("adding item to home list");
						$scope.allEventsListHome.push($scope.allEventsListHomeBackup[i]);
					}
				}
        		
        		if ($scope.allEventsListHome.length === 0) {
        			$scope.allEventsListHome = $scope.allEventsListHomeBackup;
				}
            });
        	$scope.listEventScrollCount = 1;
        	decideShowMoreButtonVisible();
        };
        //loadAllEvents();
    	
        decideShowMoreButtonVisible = function() {
        	if($scope.allEventsListHome.length > 2) {
        		$scope.showMoreButtonVisible = true;
        	} else {
        		$scope.showMoreButtonVisible = false;
        	}
        };
        
        $scope.showMoreButtonClicked = function() {
        	$scope.listEventScrollCount = $scope.listEventScrollCount + 1;
        	console.log("showMoreButtonClicked : " + $scope.listEventScrollCount);
        	if($scope.allEventsListHome.length > $scope.listEventScrollCount * 2) {
        		$scope.showMoreButtonVisible = true;
        	} else {
        		$scope.showMoreButtonVisible = false;
        	}
        };
        
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
        		loadAllEvents();
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
        								//console.log("view geo json:::"+JSON.stringify(results,null,4))
//        								 alert("city : " +  results[0].address_components[4].short_name);
        								
        								if(results[0].address_components[4].short_name.indexOf("Thane") > -1 || results[0].address_components[4].short_name.indexOf("Mumbai") > -1 ||
        								   results[0].address_components[5].short_name.indexOf("Thane") > -1 || results[0].address_components[5].short_name.indexOf("Mumbai") > -1 ||
        								   results[0].address_components[6].short_name.indexOf("Thane") > -1 || results[0].address_components[6].short_name.indexOf("Mumbai") > -1){
        									//console.log("city Thane or Mumbai::::: ");
        									$scope.$parent.selectedHomeCity = 'Mumbai';
        									$scope.$parent.selectedHomeCityId = '1';
        									console.log("after set city:::"+$scope.$parent.selectedHomeCity);
        									$scope.$apply();
        									loadAllEvents();
        									
        								}else if (results[0].address_components[4].short_name.indexOf("Pune") > -1) {
        									$scope.$parent.selectedHomeCity = 'Pune';
        									$scope.$parent.selectedHomeCityId = '2';
        									$scope.$apply();
        									loadAllEvents();
//        									$scope.allEventsListHome = [];
//        					        		for (var i = 0; i < $scope.allEventsListHomeBackup.length; i++) {
//        										if($scope.allEventsListHomeBackup[i].city.name == $scope.$parent.selectedHomeCity){
//        											$scope.allEventsListHome.push($scope.allEventsListHomeBackup[i]);
//        										}
//        									}
										}
        								else{
        									
        									$scope.$parent.selectedHomeCity = "All Cities";
        									$scope.$parent.selectedHomeCityId = undefined;
        									//$scope.allEventsListHome = $scope.allEventsListHomeBackup;
        									$scope.$apply();
        									loadAllEvents();
        									
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
    	//loadGeolocationCity();
    	if ($scope.$parent.selectedHomeCity == undefined ) {
    		loadGeolocationCity();
    		loadAllEvents();
		}else {
			loadAllEvents();
		}
    	
    	if ($scope.$parent.selectedGenre == undefined ) {
    		// don't call anything
		}else if($scope.$parent.selectedGenre != '') {
			filterEventsOnSearchBoxSearch();
		}
    	
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
        	$scope.listEventScrollCount = 1;
        	decideShowMoreButtonVisible();
        });
        
        $scope.$on('eventListSearch', function() {
        	filterEventsOnSearchBoxSearch();
        });
        
        filterEventsOnSearchBoxSearch = function() {
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
        	$scope.listEventScrollCount = 1;
        	decideShowMoreButtonVisible();
        };
        
        filterEventsOnSearchBoxSearchFromDetailsPage = function() {
        	console.log("filterEventsOnSearchBoxSearchFromDetailsPage :::  "+ $scope.$parent.selectedGenreFromDetailsPage);
        	if($scope.$parent.selectedGenreFromDetailsPage != undefined && $scope.$parent.selectedGenreFromDetailsPage != '') {
        		console.log("filterEventsOnSearchBoxSearchFromDetailsPage ::: ");
        		filterForSearchBox($scope.$parent.selectedGenreFromDetailsPage);
        		$scope.$parent.selectedGenreFromDetailsPage = undefined;
        	}
        	
        };
        filterEventsOnSearchBoxSearchFromDetailsPage();
        
        function filterForSearchBox(argument) {
        	console.log("filterForSearchBox : " + argument);
        	console.log("city here : " + $scope.$parent.selectedHomeCity);
        	if(argument != undefined && argument != '') {
        		var stringToSearch = argument.toLowerCase();
            	console.log("String to search : " + stringToSearch);
            	$scope.allEventsListHome = [];
            	var searchStrings = stringToSearch.split(" ");
            	var indexArr = [];
            	for (var index in $scope.allEventsListHomeBackup) {
            		for(var indexVar in searchStrings) {
            		   if(stringMatching($scope.allEventsListHomeBackup[index], searchStrings[indexVar]) && $scope.$parent.selectedHomeCity.toLowerCase() == $scope.allEventsListHomeBackup[index].city.name.toLowerCase()) {
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
        	}
        	if($scope.allEventsListHome.length === 0) {
        		$scope.allEventsListHome = $scope.allEventsListHomeBackup;
        	}
        	$scope.listEventScrollCount = 1;
        	decideShowMoreButtonVisible();
        };
        
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
        };
        
        $scope.$on('searchEventOnSelectedGenre', function() {
        	
        	console.log("selected city:::"+$scope.$parent.selectedHomeCity+"::and selected genre:::"+$scope.$parent.selectedGenre);
        	
        	if ($scope.$parent.selectedHomeCity == undefined && $scope.$parent.selectedGenre != undefined) {
        		$scope.allEventsListHome = [];
        		for (var i = 0; i < $scope.allEventsListHomeBackup.length; i++) {
        			if ($scope.allEventsListHomeBackup[i].genre == $scope.$parent.selectedGenre) {
        				$scope.allEventsListHome.push($scope.allEventsListHomeBackup[i]);
        			}
        		}
        	}else if ($scope.$parent.selectedHomeCity != undefined && $scope.$parent.selectedGenre != undefined) {
        		$scope.allEventsListHome = [];
        		for (var i = 0; i < $scope.allEventsListHomeBackup.length; i++) {
        			console.log("$scope.allEventsListHomeBackup[i].genre:::"+$scope.allEventsListHomeBackup[i].genre);
        			console.log("$scope.allEventsListHomeBackup[i].city.name:::"+$scope.allEventsListHomeBackup[i].city.name);
        			if ($scope.allEventsListHomeBackup[i].genre == $scope.$parent.selectedGenre && ($scope.$parent.selectedHomeCity == 'All Cities' || $scope.allEventsListHomeBackup[i].city.name == $scope.$parent.selectedHomeCity)) {
        				$scope.allEventsListHome.push($scope.allEventsListHomeBackup[i]);
        			}
        		}
        	}
        	console.log("$scope.allEventsListHome:::"+$scope.allEventsListHome.length);
        	if($scope.allEventsListHome.length === 0){
        		$scope.allEventsListHome = $scope.allEventsListHomeBackup;
        	}
        	$scope.listEventScrollCount = 1;
        	decideShowMoreButtonVisible();
        });
    });
}());