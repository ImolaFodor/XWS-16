app.controller('projectsListController', function($scope,$state, $mdDialog, projectService, ticketService, loginService, userService){
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
		loadFreeUsers();
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
	    	   $scope.project.projectTickets.push(ticket);
	    	   $scope.init();
	       });
	}
	
	$scope.addUserToProject = function(){
		var userToAdd = {};
		userToAdd.name = "";
		$mdDialog.show({
	          controller: 'addUserController',
	          templateUrl: 'module/projects/users/allUsersDialog.html',
	          clickOutsideToClose: true,
	          freeUsers: $scope.freeUsers,
	          userToAdd: userToAdd,
	          project: $scope.project
	       }).then(function(){
	    	   $scope.init();
	       });
	}
	function loadFreeUsers(){
		userService.getAllUsers(function(response){
			$scope.allUsers = response.data;
			$scope.freeUsers = angular.copy($scope.allUsers);
			var i1 = 0;
			var i2 = 0;
			var indexes = [];
			
			for(i1; i1<$scope.project.usersOnProject.length; i1++){
				i2 =0;
				for(i2; i2<$scope.freeUsers.length; i2++){
					if($scope.freeUsers[i2].id === $scope.project.usersOnProject[i1].id){
						indexes.push(i2);
					}
				}
			}
			console.log(indexes);
			while(indexes.length){
				$scope.freeUsers.splice(indexes.pop(),1);
			}
		});
	}
});