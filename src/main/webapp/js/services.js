'use strict';

/* Services */


angular.module('mowerscliServices', ['ngResource']).
    factory('SurfaceService', function($http, $resource){
      $http.defaults.useXDomain = true;
      
    	return $resource('rest/surface/:surfaceId', {}, {
    		query: {method:'GET', params:{surfaceId:''}, isArray:true}
  });
});
