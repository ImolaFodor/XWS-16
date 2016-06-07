app.controller('ticketDetailsController', function($scope, $mdDialog,PRIORITY, STATUS, ticket, ticketService,loggedUser){
	$scope.init = function(){
		$scope.ticket = ticket;
		$scope.status = STATUS;
		$scope.priority = PRIORITY;
		$scope.loggedUser = loggedUser
		console.log($scope.loggedUser);
		$scope.newComment = {};
		$scope.newComment.user = loggedUser;
		convertDates();
		$scope.oldComments = angular.copy($scope.ticket.comments);
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
		$scope.oldComments = angular.copy($scope.ticket.comments);
	}
	$scope.editComment = function(comment){
		comment.textEnable = true;
	}
	$scope.cancelChange = function(comment){
		angular.forEach($scope.oldComments, function(old){
			if(old.id){
				if(old.id === comment.id){
					comment.text = old.text;
				}
			}
			
		});
	}
	$scope.deleteComment = function(comment){
		if(comment.id){
			ticketService.deleteComment(comment.id, function(){
				
			});
		}
		var index;
		var i =0;
		for(i;i<$scope.ticket.comments.length; i++){
			if($scope.ticket.comments[i].id === comment.id){
				index = i;
			}
		}
		
		$scope.ticket.comments.splice(index, 1);
	}
	$scope.retDateFromLong = function(long){
		var date = new Date(long);
		return date;
	}
	function convertDates(){
		angular.forEach($scope.ticket.comments, function(comment){
			comment.datetime = $scope.retDateFromLong(comment.datetime);
			comment.textEnable = false;
		})
	}
	
});