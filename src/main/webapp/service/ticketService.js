app.service('ticketService', function($http){
	return {
		getTickets: function(userId, onSuccess, onError){
			$http.get("tickets/"+userId).then(onSuccess, onError);
		},
		getTicketsByPriority: function(userId, priority, onSuccess, onError){
			$http.get("tickets/"+userId+ "/"+priority).then(onSuccess, onError);
		},
		
		getPercentagesByUserOnProject: function(projectId, onSuccess, onError){
			$http.get("tickets/percentages/"+projectId).then(onSuccess, onError);
		}
	}
});