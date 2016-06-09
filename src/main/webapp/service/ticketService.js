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
		saveTicket: function(ticket, userId, onSuccess, onError){
			$http.put("tickets/"+ticket.id+"/"+userId, ticket).then(onSuccess, onError);
		},
		insertTicket: function(ticket ,onSuccess, onError){
			$http.post("tickets/",ticket).then(onSuccess, onError);
		},
		getTicketCreatedHistory: function(projectId, dateFrom, dateTo, onSuccess, onError){
			$http.get("tickets/dates/"+projectId+"/"+dateFrom+"/"+dateTo).then(onSuccess, onError);
		},
		getTicketDoneHistory: function(projectId, userId, dateFrom, dateTo, repNo, onSuccess, onError){
			$http.get("ticketchanges/dates/"+projectId+"/"+userId+"/"+dateFrom+"/"+dateTo+"/"+ repNo).then(onSuccess, onError);
		},
		deleteComment: function(ticketId, onSuccess, onError){
			$http.delete("tickets/comment/"+ticketId).then(onSuccess, onError);
		},
		getPercentegeDoneByProject: function(projectId, dateFrom ,dateTo, onSuccess, onError){
			$http.get("tickets/projectDone/"+projectId+"/"+dateFrom+"/"+dateTo+"/").then(onSuccess, onError);
		},
		getPercentegeDoneByProjectUser: function(projectId,userId, dateFrom ,dateTo, onSuccess, onError){
			$http.get("tickets/projectDone/"+projectId+"/"+userId+"/"+dateFrom+"/"+dateTo+"/").then(onSuccess, onError);
		},
	}
});