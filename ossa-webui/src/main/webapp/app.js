var myApp = angular.module('myApp', ['ngRoute']);

myApp.controller('mainController', function ($scope, $location) {
	$scope.isActive = function(route) {
        return route === $location.path();
    }  
});


myApp.controller(
	'HomeCtrl', 
	[
		'$scope', 
		'$http',
		function ($scope, $http) {
			$scope.title="Home";
			
			
		}
	]
);

myApp.controller(
	'AboutCtrl', 
	[
		'$scope', 
		'$http',
		function ($scope, $http) {
			$scope.title="About";
		}
	]
);


myApp.controller(
	'RepoCtrl', 
	[
		'$scope', 
		'$http',
		function ($scope, $http) {
			$scope.title="Respository";
		}
	]
);

myApp.controller(
	'BatchCtrl', 
	[
		'$scope', 
		'$http',
		function ($scope, $http) {
			$scope.title="Batch";
		}
	]
);

myApp.controller(
	'RestApiCtrl', 
	[
		'$scope', 
		'$http',
		function ($scope, $http) {
			$scope.title="Rest API";
		}
	]
);



myApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/', {
        templateUrl: 'page/home.html',
        controller: 'HomeCtrl'
      }).
      when('/about', {
        templateUrl: 'page/about.html',
        controller: 'AboutCtrl'
      }).
      when('/repo', {
        templateUrl: 'page/repo.html',
        controller: 'RepoCtrl'
      }).
      when('/batch', {
        templateUrl: 'page/batch.html',
        controller: 'BatchCtrl'
      }).
      when('/restapi', {
        templateUrl: 'page/restapi.html',
        controller: 'RestApiCtrl'
      }).
      otherwise({
        redirectTo: '/'
      });
  }]);