app.controller('dashboardController', function($scope, ticketService, loginService){
	$scope.init = function(){
		loginService.getProfile(function(response){
			$scope.user = response.data;
			console.log($scope.user);
			loadTickets();
		})
		
	}
	
	function loadTickets(){
		$scope.tickets = [];
		ticketService.getTickets($scope.user.id, function(response){
			$scope.tickets = response.data;
		});
	}
});