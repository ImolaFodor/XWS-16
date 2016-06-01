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
		},
		
		getPercentagesByUserOnProjectDone: function(projectId, onSuccess, onError){
			$http.get("tickets/percentagesdone/"+projectId).then(onSuccess, onError);
		},
		getTicketsByProject: function(projectId, onSuccess, onError){
			$http.get("tickets/project/"+projectId).then(onSuccess, onError);
		},
		saveTicket: function(ticket ,onSuccess, onError){
			$http.put("tickets/"+ticket.id, ticket).then(onSuccess, onError);
		},
		insertTicket: function(ticket ,onSuccess, onError){
			$http.post("tickets/",ticket).then(onSuccess, onError);
		}
	}
});