app.service('projectService', function($http){
	return{
		getProjects: function(onSuccess, onError){
			$http.get('/projects').then(onSuccess, onError);
		},
		addUserToProject: function(userId, projectId, onSuccess, onError){
			$http.post('/projects/addUser/'+userId+"/"+projectId+"/").then(onSuccess, onError);
		},
		getProject: function(projectId, onSuccess, onError){
			$http.get('projects/'+projectId).then(onSuccess,onError);
		}
		
	}
})