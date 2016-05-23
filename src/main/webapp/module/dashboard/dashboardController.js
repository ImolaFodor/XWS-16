app.controller('dashboardController', function($scope, ticketService, loginService){
	$scope.init = function(){
		loginService.getProfile(function(response){
			$scope.user = response.data;
			console.log($scope.user);
			loadTickets();
		})}
	
	$scope.priorities=['BLOCKER', 'CRITICAL', 'MAJOR', 'MINOR', 'TRIVIAL'];
	
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
	
	
});