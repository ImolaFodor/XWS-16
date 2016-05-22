app.controller('dashboardController', function($scope, ticketService, loginService){
	$scope.init = function(){
		loginService.getProfile(function(response){
			$scope.user = response.data;
			console.log($scope.user);
			loadTickets();
		})
		
	}
	
	function loadTickets(){
		ticketService.getTickets($scope.user.id, function(response){
			$scope.tickets = response.data;
			
			console.log("tickets");
			console.log($scope.tickets)
		});
	}
});