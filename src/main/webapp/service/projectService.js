app.service('projectService', function($http){
	return{
		getProjects: function(onSuccess, onError){
			$http.get('/projects').then(onSuccess, onError);
		}
	}
})