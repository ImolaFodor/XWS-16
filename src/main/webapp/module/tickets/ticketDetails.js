app.controller('ticketDetailsController', function($scope, $mdDialog, ticket){
	$scope.init = function(){
		$scope.ticket = ticket;
	}
	
	$scope.cancel = function(){
		$mdDialog.hide();
	}
});