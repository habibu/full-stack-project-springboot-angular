var myShoppingApp = angular.module('shoppinglistsystem', ['ngRoute', 'ngResource']);
myShoppingApp.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl : '/template/home.html',
        controller : 'homeController'
    })
    .when('/list-all-users', {
        templateUrl : '/template/listuser.html',
        controller : 'listUserController'
    })
    .when('/register-new-user', {
        templateUrl : '/template/registeruser.html',
        controller : 'registerUserController'
    })
    .when('/update-user/:id', {
        templateUrl : '/template/edituser.html',
        controller : 'userDetailsController'
    })
    .when('/register-item',{
        templateUrl : '/template/shoppinglist.html',
        controller : 'registerItemController'
    })
    .when('/list-all-items',{
        templateUrl : '/template/listitems.html',
        controller : 'listItemController'
    })
    .when('/update-item/:id',{
        templateUrl : '/template/editshoppinglist.html',
        controller : 'editItemController'
    })
    .when('/login', {
        templateUrl : '/login/login.html',
        controller : 'loginController'
    })
    .when('/logout', {
        templateUrl : '/login/login.html',
        controller : 'logoutController'
    })
    .otherwise({
        redirectTo : '/login'
    });
});
myShoppingApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
}]);