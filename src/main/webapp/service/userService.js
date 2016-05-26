app.service('userService', function($http){
	return{
		getUsers: function(onSuccess, onError){
			$http.get('/users').then(onSuccess, onError);
		}
	}
})