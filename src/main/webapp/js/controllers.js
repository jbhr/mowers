'use strict';

/* Controllers */

function SurfaceListCtrl($scope, SurfaceService) {
	     
  console.log("TEST");
  //$http.defaults.useXDomain = true;
  
	$scope.surfaces = SurfaceService.query({}, function(response) {
	        //good
	    	
	      },
	      function(response) {
	        //404 or bad
	    	  alert("KO:"+response.status);
	      }
	    );
  
  	
  $scope.orderProp = 'name';
  console.log("ok");
  //alert("ok"+$scope.surfaces.length);
}

//SurfaceListCtrl.$inject = ['$scope', 'SurfaceService'];

function SurfaceDetailCtrl($scope, $routeParams, SurfaceService) {
  $scope.surface = SurfaceService.get({surfaceId: $routeParams.surfaceId}, function(surface) {
    //$scope.mainImageUrl = surface.images[0];
  });
  
}

//PhoneDetailCtrl.$inject = ['$scope', '$routeParams', 'Phone'];
