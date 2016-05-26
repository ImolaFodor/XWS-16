var app = angular.module('app',['ui.router', 'ngSanitize', 'ngMessages', 'ngMaterial', 'translation', 'flow']); 

app.constant('PRIORITY', ['BLOCKER', 'CRITICAL', 'MAJOR', 'MINOR', 'TRIVIAL']);
app.constant('STATUS', ['TO_DO' , 'IN_PROGRESS', 'VERIFY', 'DONE']);

app.config(function($stateProvider, $urlRouterProvider, $translateProvider, $httpProvider, $mdThemingProvider,$mdDateLocaleProvider ){
	
	$urlRouterProvider.otherwise('/login');
	$stateProvider
    .state('login', {
        url: '/login',
        templateUrl: 'module/login.html',
        controller: 'login'
    }).state('main', {
        url: '/',
        abstract: true,
        templateUrl: 'module/main.html',
        controller: 'main'
    })
    .state('main.projects', {
        url: 'projects',
        templateUrl: 'module/projects/projectsList.html',
        controller: 'projectsListController'
    })
    .state('main.dashboard', {
        url: 'dashboard',
        templateUrl: 'module/dashboard/dashboard.html',
        controller: 'dashboardController'
    })
    .state('main.profile', {
        url: 'profile',
        templateUrl: 'module/profile/profileMain.html',
        controller: 'profileMainController'
    })
    .state('main.reports', {
        url: 'reports',
        templateUrl: 'module/reports/reports.html',
        controller: 'reportsController'
    });
	$mdThemingProvider.theme('default')
    .primaryPalette('light-green')
    .accentPalette('indigo');
	
	$mdDateLocaleProvider.formatDate = function(date) {
        return moment(date).format('DD-MM-YYYY');
    };
});