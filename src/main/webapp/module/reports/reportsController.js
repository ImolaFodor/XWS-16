app.controller('reportsController', function($scope, ticketService, loginService, userService, projectService){
	$scope.init = function(){
		console.log("REPOTS");
		loginService.getProfile(function(response){
			$scope.user = response.data;
			loadProjectsAndUsers();
		})
	}
	
	function loadProjectsAndUsers(){
		projectService.getProjects(function(response){
			$scope.projects = response.data;
		});
			
		userService.getUsers(function(response){
			$scope.users = response.data;
			
		});
	}
		$scope.openProjectReport = function(project){
			$scope.showProjectReport = true;
			$scope.project = project;
			ticketService.getPercentagesByUserOnProject($scope.project, function(response){
				$scope.reports = response.data;
		});
	}
			
	
	
});