app.controller('reportsController', function($scope, ticketService, loginService, userService, projectService){
	$scope.init = function(){
		
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
		function GetFormattedDate(thd) {
    				var Date = thd;
    				var month = format(thd.getMonth() + 1);
    				var day = format(thd.getDate());
    				var year = format(thd.getFullYear());
    				return month + "/" + day + "/" + year;
				}
	
	
		$scope.openProjectReport = function(project){
			$scope.showAllReports = true;
			$scope.project = project;
			ticketService.getPercentagesByUserOnProject($scope.project, function(response){
				$scope.reports = response.data;
				console.log($scope.reports);
				});

			ticketService.getPercentagesByUserOnProjectDone($scope.project, function(response){
				$scope.reports2 = response.data;
				console.log($scope.reports2);
			});
			
			ticketService.getTicketHistory($scope.project, $scope.datefrom, $scope.dateto, function(response){
				$scope.reports3 = response.data;
				console.log($scope.reports3);
			});
			
			}
			
	
	
});