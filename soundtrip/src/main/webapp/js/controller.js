(function () {
    var as = angular.module('angularspring');

    as.controller('MainController', function ($scope, $rootScope, $http, i18n, $location, $window) {
    	$scope.hostname = location.hostname;
    	$scope.selectedHomeCity = undefined;
    	$scope.selectedHomeCityId;
    	$scope.cityOptions = [{"id":"1","name":"Mumbai"},{"id":"2","name":"Pune"}];
    	$scope.genrelistBackup = [{"name":"Pop"},{"name":"Jazz"},{"name":"Blues"},{"name":"Classic Rock"},{"name":"Indian Classical"}];
    	$scope.genrelist = [{"name":"Pop"},{"name":"Jazz"},{"name":"Blues"},{"name":"Classic Rock"},{"name":"Indian Classical"}];
    	$scope.selectedGenre = undefined;
    	$scope.selectedGenreFromDetailsPage = undefined;
    	
    	$scope.getSelectedGenre = function(){
    		console.log("check selected genre:::"+$scope.selectedGenre);
    		$scope.genrelist = [];
    		for (var i = 0; i < $scope.genrelistBackup.length; i++) {
				if ($scope.genrelistBackup[i].name.toLowerCase().indexOf($scope.selectedGenre.toLowerCase())> -1) {
					$scope.genrelist.push($scope.genrelistBackup[i]);
				}
			}
    	}
    	
    	$scope.enableDisableMenu = function(){
    		if(window.innerWidth > 767){
    			//alert("HI"+window.innerWidth);
    			var li = document.getElementById('example-navbar-collapse');
        		console.log(li.className);
        		clsName = li.className;
        		var n = clsName.indexOf("navbar-collapse");
        		//collapse navbar-collapse
        		if(Number(n) >= 0){
        			
        			document.getElementById("viewApp").style.marginTop = "10%";
        			console.log("here1:::"+n);
        			document.getElementById("example-navbar-collapse").className = "collapse";
        			document.getElementById("example-navbar-collapse").style.setProperty("display", "none", "important");
        			document.getElementById("menuLarge").style.setProperty("height", "90px", "important");//myCarousel
        			
        			
        			
        		}else {
        			//console.log("here2::"+n);
        			document.getElementById("viewApp").style.marginTop = "12%";
        			document.getElementById("example-navbar-collapse").className = "navbar-collapse collapse";
        			document.getElementById("example-navbar-collapse").style.setProperty("display", "block", "important");
        			document.getElementById("menuLarge").style.setProperty("height", "120px", "important");
        			
    			}
    		}
    		else {
    			document.getElementById("example-navbar-collapse").style.display='';
			}
    		
    	}
    	
    	$scope.searchActive = function(){
    		console.log("on focus work");
    		document.getElementById("genreList").style.display = 'block';
    		document.getElementById("hideSearchBtn").style.display = 'block';
			//document.getElementById("cityList").style.width = '0%';
			//document.getElementById("searchEvent").style.width = '100%';
			document.getElementById("init-group").style.width = '93%';
			//document.getElementById("genreInput").style.width = '80%';    		
			document.getElementById("menuNavHead").style.marginLeft ='60px';
    	}
    	
    	
    	getCityOptions = function() {
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
    		var url = window.location.href;
    		if (url.indexOf('eventdetails') > -1) {
    			$scope.selectedGenreFromDetailsPage = $scope.selectedGenre;
        		$window.location.href = '#/eventhome';
			} else {
				$scope.selectedGenreFromDetailsPage = undefined;
				$rootScope.$broadcast('searchEventOnSelectedGenre');
			}
    	};
    	
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
        
        $scope.hideSearchEvent= function(){
        	document.getElementById("searchBtnGenere").style.display = 'none';
    		document.getElementById("activeSearchBtn").style.display = 'block';
    		document.getElementById("genreInput").style.display = 'none';
    		document.getElementById("genreList").style.display = 'none';
    		document.getElementById("hideSearchBtn").style.display = 'none';
    		document.getElementById("selectHeaderDiv").style.display = 'table';
    		document.getElementById("headerLogoDiv").style.display = 'table-cell';
    		document.getElementById("menuNavHead").style.marginLeft ='38px';
        }

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
        
        $scope.citySelected = function(selectedCity) {
        	var url = window.location.href;
        	$scope.selectedHomeCity = selectedCity;
        	if (url.indexOf('eventdetails') > -1) {
        		$window.location.href = '#/eventhome';
			}else{
				$rootScope.$broadcast('eventListFiltered');
			}
    	};
    	
    	$scope.searchClicked = function() {
    		var url = window.location.href;
    		if (url.indexOf('eventdetails') > -1) {
    			$window.location.href = '#';
			}else{
				$rootScope.$broadcast('eventListSearch');
			}
    	};
    	
    	$scope.getSearchActive = function(){
    		//searchBtnGenere,activeSearchBtn,genreInput,selectHeaderDiv, headerLogoDiv
    		document.getElementById("searchBtnGenere").style.display = 'block';
    		document.getElementById("activeSearchBtn").style.display = 'none';
    		document.getElementById("genreInput").style.display = 'block';
    		document.getElementById("selectHeaderDiv").style.display = 'none';
    		document.getElementById("headerLogoDiv").style.display = 'none';
    		document.getElementById("genreInput").focus();
    		$scope.searchActive();
    	}
    });

    as.controller('AdminController', function ($scope, $http) {
        $http.get('action/user');
    });
    
}());