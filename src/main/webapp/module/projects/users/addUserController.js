app.controller('addUserController', function($scope, $mdDialog, userToAdd, projectService, freeUsers, project){
	$scope.init = function(){
		$scope.userToAdd = userToAdd;
		$scope.freeUsers = freeUsers;
		$scope.project = project;
	}
	
	$scope.addUser = function(u){
		$scope.userToAdd = u;
		angular.forEach($scope.freeUsers, function(user){
			user.selected = false;
		})
		u.selected = true;
	}
	
	$scope.cancel = function(){
		$mdDialog.hide();
	}
	$scope.save = function(){
		projectService.addUserToProject($scope.userToAdd.id, $scope.project.id, function(response){
			$scope.cancel();
		})
	}
});