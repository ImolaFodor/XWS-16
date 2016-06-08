app.service('userService', function($http){
	return{
		getUsers: function(onSuccess, onError){
			$http.get('/users').then(onSuccess, onError);
		},
		getAllUsers: function(onSuccess, onError){
			$http.get('/users/getAllUsers/').then(onSuccess, onError);
		}
	}
})