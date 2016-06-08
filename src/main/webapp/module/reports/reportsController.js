app.controller('reportsController', function($scope, ticketService,
		loginService, userService, projectService) {
	$scope.init = function() {

		loginService.getProfile(function(response) {
			$scope.user = response.data;
			loadProjectsAndUsers();
		});
		$scope.startDateReport3 = new Date();
		$scope.endDateReport3 = new Date();
	}

	function loadProjectsAndUsers() {
		projectService.getProjects(function(response) {
			$scope.projects = response.data;
		});

		userService.getUsers(function(response) {
			$scope.users = response.data;

		});
	}
	/*$scope.getFormattedDate = function(thd) {
		var Date = thd;
		var month = format(thd.getMonth() + 1);
		var day = format(thd.getDate());
		var year = format(thd.getFullYear());
		return month + "/" + day + "/" + year;
	}*/

	$scope.openProjectReport = function(project) {
		$scope.showAllReports = true;
		$scope.project = project;
		ticketService.getPercentagesByUserOnProject($scope.project, function(
				response) {
			$scope.reports = response.data;
		});

		ticketService.getPercentagesByUserOnProjectDone($scope.project,
				function(response) {
					$scope.reports2 = response.data;
				});
	}
	
	$scope.getReport3 = function(){
		ticketService.getTicketCreatedHistory($scope.project, $scope.startDateReport3,
				$scope.endDateReport3, function(response) {
					$scope.reports3 = response.data;
					angular.forEach($scope.reports3, function(report){
						report.date = $scope.retDateFromLong(report.date);
						
					})
					$scope.showReport3 = true;
		});
	}
	
	$scope.getReport45 = function(repNo){
		$scope.repNo=repNo;
		
		ticketService.getTicketDoneHistory($scope.project, $scope.user.id, $scope.startDateReport3,
				$scope.endDateReport3, $scope.repNo, function(response) {
					
					$scope.reports45 = response.data;
					angular.forEach($scope.reports3, function(report){
						report.date = $scope.retDateFromLong(report.date);
					})
					
		if (repNo==4){
    		$scope.showReport3 = false;
    		$scope.showReport4 = true;
    		$scope.showReport5 = false;
		} else if(repNo==5){
    		$scope.showReport3 = false;
    		$scope.showReport4 = false;
    		$scope.showReport5 = true;
		}
		
		});
	}
	
	$scope.retDateFromLong = function(long){
		var date = new Date(long);
		return date;
	}

});