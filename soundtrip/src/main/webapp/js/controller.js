(function () {
    var as = angular.module('angularspring');

    as.controller('MainController', function ($scope, $rootScope, $http, i18n, $location) {
    	$scope.selectedHomeCity;
    	$scope.selectedHomeCityId;
    	$scope.cityOptions = [{"id":"1","name":"Mumbai"},{"id":"2","name":"Pune"}];
    	$scope.genrelistBackup = [{"name":"Pop"},{"name":"Jazz"},{"name":"Blues"},{"name":"Classic Rock"},{"name":"Indian Classical"}];
    	$scope.genrelist = [{"name":"Pop"},{"name":"Jazz"},{"name":"Blues"},{"name":"Classic Rock"},{"name":"Indian Classical"}];
    	$scope.selectedGenre = undefined;
    	
    	$scope.getSelectedGenre = function(){
    		console.log("check selected genre:::"+$scope.selectedGenre);
    		$scope.genrelist = [];
    		for (var i = 0; i < $scope.genrelistBackup.length; i++) {
				if ($scope.genrelistBackup[i].name.toLowerCase().indexOf($scope.selectedGenre.toLowerCase())> -1) {
					$scope.genrelist.push($scope.genrelistBackup[i]);
				}
			}
    	}
    	
    	$scope.searchActive = function(){
    		console.log("on focus work");
    		document.getElementById("genreList").style.display = 'block';
			document.getElementById("cityList").style.width = '0%';
			document.getElementById("searchEvent").style.width = '100%';
			document.getElementById("init-group").style.width = '93%';
    		
    	}
    	
    	
    	getCityOptions = function() {
    		alert('calling getcityOptions');
    		$http.get("action/getallcities").success(function (data) {
        		$scope.cityOptions = data;
        		console.log("check city json:::"+JSON.stringify($scope.cityOptions,null,4));
            });
    	};
    	
    	$scope.genreListClick = function(selectedFrmList){
    		//console.log("selected from list:::"+selectedFrmList);
    		document.getElementById("genreInput").value = selectedFrmList;
    		$scope.selectedGenre = selectedFrmList;
    		console.log("selected from list:::"+$scope.selectedGenre);
    		document.getElementById("genreList").style.display = 'none';
    		$rootScope.$broadcast('searchEventOnSelectedGenre');
    	}
    	
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
        	$rootScope.$broadcast('eventListFiltered');
    	};
    	
    	$scope.searchClicked = function() {
        	$rootScope.$broadcast('eventListSearch');
    	};
    });

    as.controller('AdminController', function ($scope, $http) {
        $http.get('action/user');
    });
}());