app.controller('login', function($scope, $state, $mdDialog, $translate, loginService){
	$scope.username = "";
	$scope.password = "";
	$scope.nePostojiKorisnik = false;
	$scope.neispravnaLoznika = false;
	$scope.user  = {};
	$scope.activation = {};
	$scope.repeat_password ="";
	$scope.showRegisterForm = false;
	$scope.showLoginForm = true;
	$scope.passNotEqual = true;
	$scope.activationForm = false;
	$scope.userExists = false;
    $scope.loginUser = function(ev){
        loginService.login ($scope.username, $scope.password, function(response){
        	loginService.getProfile(function(response){
        		$scope.user = response.data;
        		$state.transitionTo('main.dashboard');
        	});
        	
            
        }, function(response){
        	console.log(response);
        	if(response.data == "BAD_PASSWORD"){
        		$scope.neispravnaLoznika = true;
        	}else if(response.data == "NO_USER"){
        		console.log("NO USERR")
        		$scope.neispravnaLoznika = true;
        		$scope.nePostojiKorisnik = true;
        	}else if(response.data == "USER_NOT_ACTIVATED"){
        		 $mdDialog.show(
   				      $mdDialog.alert()
   				        .parent(angular.element(document.querySelector('#popupContainer')))
   				        .clickOutsideToClose(true)
   				        .title($translate.instant('USER_NOT_ACTIVATED'))
   				        .textContent($translate.instant('USER_NOT_ACTIVATED_MESSAGE'))
   				        .ariaLabel($translate.instant('USER_NOT_ACTIVATED'))
   				        .ok($translate.instant('OK'))
   				        .targetEvent(ev)
   				    ).then(function(){
   				     $scope.openActivationForm();
   				    });
        	}
        	
        });
   };
   
});