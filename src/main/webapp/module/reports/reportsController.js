app.controller('reportsController', function($scope, $state, ticketService,
		loginService, userService, projectService) {
	$scope.init = function() {
		loginService.getProfile(function(response) {
			if(response.data.id){
				$scope.user = response.data;
				loadProjectsAndUsers();
			}else{
				$state.transitionTo('login');
			}
		}, function(response){
			$state.transitionTo('login');
		});
		$scope.startDateReport3 = new Date();
		$scope.endDateReport3 = new Date();
		$scope.startDate4 = new Date();
		$scope.endDate4 = new Date();
		
		$scope.startDate5 = new Date();
		$scope.endDate5 = new Date();
		
	}



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
	$scope.getReportGetDoneByProject = function(){
		ticketService.getPercentegeDoneByProject($scope.project, $scope.startDate4, $scope.endDate4, function(response){
			$scope.reportDoneProject = response.data;
			angular.forEach($scope.reportDoneProject, function(report){
				report.date = $scope.retDateFromLong(report.date);
			})
			$scope.showReport4 = true;
		})
	}
	$scope.getReportGetDoneByProjectUser = function(){
		alert("DASD");
		ticketService.getPercentegeDoneByProjectUser($scope.project,$scope.user.id, $scope.startDate5, $scope.endDate5, function(response){
			alert("DALLLL");
			$scope.reportDoneProjectUser = response.data;
			angular.forEach($scope.reportDoneProjectUser, function(report){
				report.date = $scope.retDateFromLong(report.date);
			})
			$scope.showReport5 = true;
		})
	}
	$scope.getReport45 = function(repNo){
		$scope.repNo=repNo;
		
		ticketService.getTicketDoneHistory($scope.project, $scope.user.id, $scope.startDateReport3,
				$scope.endDateReport3, $scope.repNo, function(response) {
					
					$scope.reports45 = response.data;
					angular.forEach($scope.reports45, function(report){
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
	
	function loadProjectsAndUsers() {
		projectService.getProjects(function(response) {
			$scope.projects = response.data;
		});

		userService.getUsers(function(response) {
			$scope.users = response.data;

		});
	}

});