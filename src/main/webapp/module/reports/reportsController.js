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
		ticketService.getTicketHistory($scope.project, $scope.startDateReport3,
				$scope.endDateReport3, function(response) {
					$scope.reports3 = response.data;
					angular.forEach($scope.reports3, function(report){
						report.date = $scope.retDateFromLong(report.date);
					})
					$scope.showReport3 = true;
		});
	}
	$scope.retDateFromLong = function(long){
		var date = new Date(long);
		return date;
	}

});