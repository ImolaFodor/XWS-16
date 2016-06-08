app.controller('projectsListController', function($scope,$state, $mdDialog, projectService, ticketService, loginService){
	$scope.init = function(){
		loginService.getProfile(function(response){
			console.log(response);
			if(response.data.id){
				$scope.loggedUser = response.data;
			}else{
				$state.transitionTo('login');
			}
		},function(response){
			$state.transitionTo('login');
		});
		$scope.projects = [];
		projectService.getProjects(function(response){
			$scope.projects = response.data;
			console.log($scope.projects);
			angular.forEach($scope.projects, function(project){
				project.projectTickets = [];
				ticketService.getTicketsByProject(project.id, function(response){
					project.projectTickets = response.data;
				});
			});
		})
		
	};
	
	$scope.openProjectDetails = function(project){
		$scope.showProjectDetails = true;
		$scope.project = project;
	}
	
	$scope.openTicketDetails = function(ticket){
		$mdDialog.show({
	          controller: 'ticketDetailsController',
	          templateUrl: 'module/tickets/ticketDetails.html',
	          clickOutsideToClose: true,
	          loggedUser: $scope.loggedUser,
	          ticket: ticket
	       });
	}
	
	$scope.createNewTicket = function(){
		var ticket = {};
		ticket.project = $scope.project;
		ticket.ticketCreator = $scope.loggedUser;
		$mdDialog.show({
	          controller: 'ticketDetailsController',
	          templateUrl: 'module/tickets/ticketDetails.html',
	          clickOutsideToClose: true,
	          loggedUser: $scope.loggedUser,
	          ticket: ticket
	       }).then(function(){
	    	   //$scope.project.projectTickets.push(ticket);
	    	   $scope.init();
	       });
	}
});