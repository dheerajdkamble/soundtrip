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
});
}());