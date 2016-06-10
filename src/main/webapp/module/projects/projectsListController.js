app.controller('projectsListController', function($scope,$state, $mdDialog, projectService, ticketService, loginService, userService){
	$scope.init = function(){
		loginService.getProfile(function(response){
			if(response.data.id){
				$scope.loggedUser = response.data;
				if($scope.loggedUser.role === 'ADMIN'){
					projectService.getProjects(function(response){
						$scope.projects = response.data;
						angular.forEach($scope.projects, function(project){
							project.projectTickets = [];
							ticketService.getTicketsByProject(project.id, function(response){
								project.projectTickets = response.data;
								angular.forEach(project.projectTickets, function(ticket){
									ticket.dateCreated = retDateFromLong(ticket.dateCreated);
								});
							});
							
						});
					});
				}else{
					projectService.getProjectByUser($scope.loggedUser.id, function(response){
						$scope.projects = response.data;
						angular.forEach($scope.projects, function(project){
							project.projectTickets = [];
							ticketService.getTicketsByProject(project.id, function(response){
								project.projectTickets = response.data;
								angular.forEach(project.projectTickets, function(ticket){
									ticket.dateCreated = retDateFromLong(ticket.dateCreated);
								});
							});
							
						});
					});
				}
			}else{
				$state.transitionTo('login');
			}
		},function(response){
			$state.transitionTo('login');
		});
		$scope.projects = [];
		
		
		$scope.isNew = false;
	};
	
	$scope.openProjectDetails = function(project){
		$scope.showProjectDetails = true;
		$scope.isNew = false;
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
	       }).then(function(){
	    	   loadProject();
	       });
	}
	
	$scope.createNewTicket = function(){
		var ticket = {};
		ticket.project = $scope.project;
		ticket.ticketCreator = $scope.loggedUser;
		ticket.label = $scope.project.label +"-"+ ($scope.project.ticketsNum+1).toString();
		$mdDialog.show({
	          controller: 'ticketDetailsController',
	          templateUrl: 'module/tickets/ticketDetails.html',
	          clickOutsideToClose: true,
	          loggedUser: $scope.loggedUser,
	          ticket: ticket
	       }).then(function(){
	    	   loadProject();
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
	    	   loadProject();
	       });
	}
	
	$scope.removeUser = function(user){
		projectService.removeUserFromProject(user.id, $scope.project.id, function(response){
			loadProject();
		})
	}
	
	$scope.addNewProject = function(){
		$scope.showProjectDetails = true;
		$scope.isNew = true;
		$scope.project = {};
		$scope.project.label = "";
		$scope.project.name = "";
		
	}
	
	$scope.cancelSaveProject = function(){
		if($scope.project.id){
			$scope.init();
		}
		$scope.showProjectDetails = false;
		$scope.isNew = false;
		$scope.project = {};
	}
	
	$scope.saveProject = function(){
		if($scope.project.id){
			projectService.updateProject($scope.project, function(response){
				$scope.project = {};
				$scope.isNew = false;
				$scope.showProjectDetails = false;
				$scope.init();
			})
		}else{
			projectService.insertProject($scope.project, function(response){
				$scope.projects.push($scope.project);
				$scope.project = {};
				$scope.isNew = false;
				$scope.showProjectDetails = false;
				$scope.init();
			})
		}
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
			while(indexes.length){
				$scope.freeUsers.splice(indexes.pop(),1);
			}
		});
	}
	function loadProject(){
		projectService.getProject($scope.project.id, function(response){
			$scope.project = response.data;
			ticketService.getTicketsByProject($scope.project.id, function(response){
				$scope.project.projectTickets = response.data;
				angular.forEach($scope.project.projectTickets, function(ticket){
					ticket.dateCreated = retDateFromLong(ticket.dateCreated);
				});
			}, function(response){
			});
		});
	}
	
	function retDateFromLong(long){
		var date = new Date(long);
		return date;
	}
});