app.controller('projectsListController', function($scope, projectService){
	$scope.init = function(){
		$scope.projects = [];
		projectService.getProjects(function(response){
			$scope.projects = response.data;
		})
	};
});