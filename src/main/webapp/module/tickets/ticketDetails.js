app.controller('ticketDetailsController', function($scope, $mdDialog,PRIORITY, STATUS, ticket, ticketService,loggedUser, projectService){
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
			$scope.ticket.project.projectTickets = [];
			ticketService.saveTicket($scope.ticket, $scope.loggedUser, function(response){
				$scope.cancel();
			});
		}else{
			//var project = angular.copy($scope.project)
			$scope.ticket.project.projectTickets = [];
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
	
	$scope.deleteTicket = function(){
		alert("DELETE")
		projectService.deleteTicket($scope.ticket.id, $scope.ticket.project.id, function(response){
			$scope.cancel();
		}, function(response){
			console.log(response);
		});
	}
	function convertDates(){
		angular.forEach($scope.ticket.comments, function(comment){
			comment.datetime = $scope.retDateFromLong(comment.datetime);
			comment.textEnable = false;
		})
		
		angular.forEach($scope.ticket.ticketChanges, function(change){
			change.date_time = $scope.retDateFromLong(change.date_time);
		})
	}
});