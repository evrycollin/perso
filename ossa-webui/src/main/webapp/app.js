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
			$scope.title="OSSA Fundation";
			
			
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

			
			$scope.reload = function() {
				$scope.edited = null;
				$scope.created = null;
				$scope.repo = null;
				$http.get('repo/packages.json').success(
					function(data, status, headers, config) {
						$scope.packages = data;
					}
				);

				$http.get('repo/export-all.json').success(
					function(data, status, headers, config) {
						$scope.repo = data;
						$scope.totalRepoSize=0;
						for( e in $scope.repo ) {
							$scope.totalRepoSize += $scope.repo[e].content.length;
						}
					}
				);
			};
			
			$scope.add = function() {
				$scope.created = {};
			
			};
			
			$scope.saveNew = function() {
				$scope.repo.push( $scope.created );
				$scope.totalRepoSize+=$scope.created.content.length;
				$scope.created = null;	
			};
			
			
			$scope.edit = function(entry) {			
				$scope.edited = entry;
			};

			$scope.save = function() {				
				// post to server
				$http.post('/repo/save', $scope.edited )
				.success(function(data, status, headers, config) {
					$scope.edited = null;
				})
				.error(function(data, status, headers, config) {
					console.log("error" + status );
				});			
				
			};
			
			
			$scope.reload();
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