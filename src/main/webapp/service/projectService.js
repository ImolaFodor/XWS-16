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
		},
		removeUserFromProject: function(userId, projectId, onSuccess, onError){
			$http.delete('projects/removeUser/'+userId+"/"+projectId+"/").then(onSuccess,onError);
		},
		deleteTicket: function(ticketId, projectId, onSuccess, onError){
			$http.delete('projects/removeTicket/'+ticketId+"/"+projectId+"/").then(onSuccess,onError);
		},
		insertProject: function(project, onSuccess, onError){
			$http.post('projects/', project).then(onSuccess, onError);
		},
		updateProject: function(project, onSuccess, onError){
			$http.put('projects/', project).then(onSuccess, onError);
		},
		getProjectByUser: function(userId, onSuccess, onError){
			$http.get('projects/byUser/'+userId+"/").then(onSuccess, onError);
		}
		
	}
})