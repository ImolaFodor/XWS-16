app.service('ticketService', function($http){
	return {
		getTickets: function(userId, onSuccess, onError){
			$http.get("tickets/"+userId).then(onSuccess, onError);
		}
	}
});