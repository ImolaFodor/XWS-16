app.controller('ticketDetailsController', function($scope, $mdDialog,PRIORITY, STATUS, ticket, ticketService,loggedUser){
	$scope.init = function(){
		$scope.ticket = ticket;
		$scope.status = STATUS;
		$scope.priority = PRIORITY;
		$scope.loggedUser = loggedUser
		console.log($scope.loggedUser);
		$scope.newComment = {};
		$scope.newComment.user = loggedUser;
	}
	
	$scope.cancel = function(){
		$mdDialog.hide();
	}
	$scope.save = function(){
		if($scope.ticket.id){
			$scope.ticket.dateCreated = new Date();
			ticketService.saveTicket($scope.ticket, function(response){
				$scope.cancel();
			});
		}else{
			$scope.ticket.ticketCreator = $scope.loggedUser;
			$scope.ticket.dateCreated = new Date();
			ticketService.insertTicket($scope.ticket, function(response){
				$scope.cancel();
			});
		}
	}
	
	$scope.addComment = function(){
		$scope.newComment.datetime = new Date();
		var com = $scope.newComment
		$scope.ticket.comments.push(com);
		$scope.newComment = {};
	}
});