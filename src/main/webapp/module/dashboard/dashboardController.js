app.controller('dashboardController', function($scope, $mdDialog, ticketService, loginService, STATUS, PRIORITY){
	$scope.init = function(){
		loginService.getProfile(function(response){
			$scope.user = response.data;
			console.log($scope.user);
			loadTickets();
		});
	}
	
	$scope.search = {};
	
	$scope.priority = PRIORITY;
	$scope.status = STATUS;
	$scope.priority.unshift("");
	$scope.status.unshift("");
	function loadTickets(){
		ticketService.getTickets($scope.user.id, function(response){
			$scope.tickets = response.data;
			
			console.log("tickets");
			console.log($scope.tickets)
		});
	}
	
	$scope.getFilteredTickets = function(){
		ticketService.getTicketsByPriority($scope.user.id, $scope.selected, function(response){
				$scope.filtered_tickets = response.data;
				console.log($scope.filtered_tickets)
			})
	}
	
	$scope.openTicketDetails = function(ticket){
		$mdDialog.show({
	          controller: 'ticketDetailsController',
	          templateUrl: 'module/tickets/ticketDetails.html',
	          clickOutsideToClose: true,
	          loggedUser: $scope.user,
	          ticket: ticket
	       }).then(function(){
	    	  loadTickets();
	       });
	}
});