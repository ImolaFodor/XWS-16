app.controller('projectsListController', function($scope, $mdDialog, projectService){
	$scope.init = function(){
		$scope.projects = [];
		projectService.getProjects(function(response){
			$scope.projects = response.data;
		})
	};
	
	$scope.openProjectDetails = function(project){
		$scope.showProjectDetails = true;
		$scope.project = project;
		$scope.project.projectTickets.push({
			name: 'Ticket1'
		});
		$scope.project.projectTickets.push({
			name: 'Ticket2'
		});
		
		$scope.project.projectTickets.push({
			name: 'Ticket3'
		});
	}
	
	$scope.openTicketDetails = function(ticket){
		$mdDialog.show({
	          controller: 'ticketDetailsController',
	          templateUrl: 'module/tickets/ticketDetails.html',
	          clickOutsideToClose: true,
	          ticket: ticket
	       });
	}
	
	$scope.createNewTicket = function(){
		$mdDialog.show({
	          controller: 'ticketDetailsController',
	          templateUrl: 'module/tickets/ticketDetails.html',
	          clickOutsideToClose: true,
	          ticket: {}
	       });
	}
});