var app = angular.module('test-app', [ 'restangular','ngAnimate' ]);

app.controller('TestCtrl', function($scope, Restangular ) {

	$scope.user={};
	$scope.groups=[];
	$scope.address={};
	$scope.task;
	$scope.tasks;
	$scope.event;
	$scope.events;
	$scope.location;
	$scope.locations;
	
	$scope.confirmDeleteMessage=null;
	$scope.entityToDelete=null;
	$scope.entityDeleted=false;
	$scope.deleteRefresh=null;

	$scope.reloadTasks = function() { 
		$scope.task=null;
		$scope.tasks=null;
		$scope.event=null;
		$scope.events=null;
		$scope.location=null;
		$scope.locations=null;
		// load user
		var api = Restangular.one('../login', 2);
		
		api.get().then( function(user) {		
			Restangular.one('User', user.id ).get().then( function(user) {
				$scope.user = user;
				
				// load user tasks
				$scope.user.getList('tasks').then( function(tasks) {
					$scope.tasks = tasks;
				} );
				$scope.user.getList('groups').then( function(groups) {
					$scope.groups = groups;
				} );				
				// load user address		
				user.oneUrl('address', user.address._link).get().then( function(address) {
					$scope.address = address;			
				} );				
			});
			
		});	
	}

	$scope.reloadTasks();
	
	$scope.confirmedDelete = function() {
		$scope.entityDeleted=false;
		$scope.entityToDelete.remove().then( function() {
			$scope.entityDeleted=true;
			$scope.entityToDelete=null;
			$scope.confirmDeleteMessage=null;
			if( $scope.deleteRefresh ) {
				$scope.deleteRefresh();
			}
			$scope.deleteRefresh=null;
			$('#confimrDeleteModal').modal('hide');
		} );
	}
	$scope.canceledDelete = function() {
		$scope.entityDeleted=false;
		$scope.entityToDelete=null;
		$scope.confirmDeleteMessage=null;
		$('#confimrDeleteModal').modal('hide');
	}
	
	
	$scope.deleteTask= function(task) {
		$scope.confirmDeleteMessage='Are you shure you want to delete task '+task.taskName+' ?';
		$scope.entityToDelete = task;
		$scope.entityDeleted = false;
		$scope.deleteRefresh = function() {
			$scope.reloadTasks();		
		}
		$('#confimrDeleteModal').modal('show');
	}
	$scope.editTask = function(task) {
		$scope.task = Restangular.copy(task);
		$('#editTaskModal').modal('show');
	}
	$scope.createTask = function(task) {
		$scope.task = { user: { id: $scope.user.id } };
		$('#editTaskModal').modal('show');
	}
	$scope.createOrUpdateTask = function() {
		if( $scope.task.id ) {
			$scope.task.put().then( function() {
				$('#editTaskModal').modal('hide');		
				$scope.reloadTasks();			
			} );
		} else {
			$scope.user.post("tasks", $scope.task).then( function() {
				$('#editTaskModal').modal('hide');		
				$scope.reloadTasks();			
			} );
		}
	}	
	
	$scope.saveTask= function(task) {
		task.put().then( function() {	
			$scope.reloadTasks();			
		});		
	}
	
	
	

	$scope.viewEvents = function( task ) {
		$scope.closeEvents();
		$scope.task=task;
		task.getList('events').then( function(events) {
			$scope.events = events;
		} );
	}
	$scope.closeEvents = function() {
		$scope.closeLocations();
		$scope.task=null;
		$scope.events=null;
		$scope.event=null;
	}
	$scope.deleteEvent = function(event) {
		$scope.confirmDeleteMessage='Are you shure you want to delete event '+event.description+' ?';
		$scope.entityToDelete = event;
		$scope.entityDeleted = false;
		$scope.deleteRefresh = function() {
			$scope.viewEvents($scope.task);			
		}
		$('#confimrDeleteModal').modal('show');
	}
	$scope.saveEvent = function(event) {
		event.put().then( function() {			
			$scope.viewEvents($scope.task);			
		});		
	}
	$scope.editEvent = function(event) {
		$scope.event = Restangular.copy(event);
		$('#editEventModal').modal('show');
	}
	$scope.createEvent = function(event) {
		$scope.event = { task: { id: $scope.task.id} };
		$('#editEventModal').modal('show');
	}
	$scope.createOrUpdateEvent = function() {
		if( $scope.event.id ) {
			$scope.event.put().then( function() {
				$('#editEventModal').modal('hide');		
				$scope.viewEvents($scope.task);			
			} );
		} else {
			$scope.task.post("events", $scope.event).then( function() {
				$('#editEventModal').modal('hide');		
				$scope.viewEvents($scope.task);			
			} );
		}
	}	

	$scope.viewLocations = function(event) {
		$scope.closeLocations();
		$scope.event = event;
		event.getList('locations').then( function(locations) {
			$scope.locations = locations;
		} );		
	}
	$scope.closeLocations = function() {
		$scope.locations=null;
	}
	$scope.deleteLocation = function(location) {
		$scope.confirmDeleteMessage='Are you shure you want to delete location '+location.location+' ?';
		$scope.entityToDelete = location;
		$scope.entityDeleted = false;
		$scope.deleteRefresh = function() {
			$scope.viewLocations($scope.event);			
		}
		$('#confimrDeleteModal').modal('show');
	}
	$scope.saveLocation = function(location) {
		location.put().then( function() {			
			$scope.viewLocations($scope.event);			
		});		
	}
	$scope.editLocation = function(location) {
		$scope.location = Restangular.copy(location);
		$('#editLocationModal').modal('show');
	}
	$scope.createLocation = function() {
		$scope.location = {
				"location" : "enter a location",
				"event" : {
					"id" : $scope.event.id
				}
		};
		$('#editLocationModal').modal('show');
	}
	$scope.createOrUpdateLocation = function() {
		if( $scope.location.id ) {
			$scope.location.put().then( function() {
				$('#editLocationModal').modal('hide');
				$scope.viewLocations($scope.event);			
			});
		} else {
			$scope.event.post("locations", $scope.location).then( function() {
				$('#editLocationModal').modal('hide');
				$scope.viewLocations($scope.event);			
			});
		}
	}	

});

//Global configuration
app.config(function(RestangularProvider) {
	RestangularProvider.setBaseUrl('/fastrest');
	RestangularProvider.setRestangularFields({
		  selfLink: '_link'
	});	
	
});