app.controller('profileMainController', function($scope,$state,$mdDialog,$translate, loginService, userService, GENDERS){
	$scope.init = function(){
		$scope.genders = GENDERS;
		$scope.successCreated = false;
		$scope.passNotEqual = true;
		$scope.repeat_password ="";
		loginService.getProfile(function(response){
			if(response.data.id){
				$scope.loggedUser = response.data;
			}else{
				$state.transitionTo('login');
			}
		},function(response){
			$state.transitionTo('login');
		});
	};
	
	$scope.saveUser = function(ev){
		 if($scope.loggedUser.password != $scope.repeat_password){
			   $scope.repeat_password = "";
			   return;
		   }
		 userService.saveUser($scope.loggedUser, function(response){
			 $mdDialog.show(
				      $mdDialog.alert()
				        .parent(angular.element(document.querySelector('#popupContainer')))
				        .clickOutsideToClose(true)
				        .title('Success')
				        .textContent($translate.instant('YOU HAVE UPDATED YOUR PROFILE (changes will be visible when you login again)'))
				        .ariaLabel($translate.instant('Success'))
				        .ok($translate.instant('OK'))
				        .targetEvent(ev)
				    );
		 });
		 
	}
	$scope.cancel = function(){
		$scope.init();
	}
});