app.controller('main', function($scope, $state, $mdUtil, $mdSidenav, loginService){

    $scope.init = function(){
        
    };

    $scope.toggleNavigation = $mdUtil.debounce(function(){
        $mdSidenav('left').toggle()
    }, 200);

    $scope.logout = function(){
        loginService.logout()
        $state.transitionTo('login')
    };

});