myShoppingApp.controller('homeController', function($http, $scope, $location, $route, $rootScope) {
    if ($rootScope.authenticated) {

        $location.path("/");
        $scope.loginerror = false;
    } else {
        $location.path("/login");
        $scope.loginerror = true
    }
});

myShoppingApp.controller('loginController', function($rootScope, $http, $scope, $location, $route) {

    $scope.credentials = {};
    $scope.resetForm = function() {
        $scope.credentials = null;
    }
    var authenticated = function(credentials, callback) {
        var headers = $scope.credentials ? {
            authorization : "Basic " + btoa($scope.credentials.username + ":" + $scope.credentials.password)} : {};
        $http.get('user', {headers : headers}).then(
            function(response) {

                if (response.data.name) {
                    $rootScope.authenticated = true;
                } else {
                    $rootScope.authenticated = false;
                }
                callback && callback();
            }, ()=> {
                $rootScope.authenticated = false;
                callback && callback();
            });
    }
    authenticated();
    $scope.loginUser = function() {
        authenticated($scope.credentials, function() {

            if ($rootScope.authenticated) {
                $location.path("/");
                $scope.loginerror = false;
            } else {
                $location.path("/login");
                $scope.loginerror = true;
            }
        });
    }
});

myShoppingApp.controller('logoutController', function($rootScope, $http, $location) {
    $http.post('logout', {}).finally( function() {
        $rootScope.authenticated = false;
        $location.path("/");
    });
});

myShoppingApp.controller('registerUserController', function($scope, $http, $location, $route) {
    $scope.submitUserForm = function (){
            $http({
                method : "POST",
                url: "http://localhost:8080/api/user/",
                data: $scope.user
            }).then( function(response) {
                $location.path("/list-all-users");
                $route.reload();
            }, function(errResponse) {
                $scope.errorMessage = errResponse.data.errorMessage;
            });
        }
        $scope.resetForm = function() {
            $scope.user = null;
        }
    
});

myShoppingApp.controller('registerItemController', function($http,$scope, $location, $route) {
    $scope.submitItemForm = function() {
            $http({
                method : "POST",
                url : "http://localhost:8080/api/item/",
                data : $scope.item
            }).then( function(response) {
                $location.path("/list-all-items");
                $route.reload();
            }, function(errResponse) {
                $scope.errorMessage = errResponse.data.errorMessage;
            });
        }
        $scope.resetForm = function() {
            $scope.item = null;
        }
        
});

myShoppingApp.controller('listUserController', function($scope, $route, $location, $http) {
    $http({
        method : "GET",
        url : "http://localhost:8080/api/user/"
    }).then( function(response) {
        $scope.users = response.data;
    });
    $scope.editUser = function(userID) {
        $location.path("/update-user/" + userID);
    }
    $scope.deleteUser = function(userID) {
        $http({
            method : "DELETE",
            url : "http://localhost:8080/api/user/" + userID
        }).then( function(response) {
            $location.path("/list-all-users");
            $route.reload();
        });
    }
});

myShoppingApp.controller('listItemController', function($scope, $route, $location, $http) {
    $http({
        method : "GET",
        url : "http://localhost:8080/api/item/"
    }).then( function(response) {
        $scope.items = response.data;
    });
    $scope.editItem = function (itemID) {
        $location.path("/update-item/" + itemID);
    }
    $scope.deleteItem = function(itemID) {
        $http({
            method : "DELETE",
            url : "http://localhost:8080/api/item/" + itemID
        }).then( function() {
            $location.path("/list-all-items");
            $route.reload();
        })
    }
});

myShoppingApp.controller('userDetailsController', function($scope, $http, $routeParams, $location, $route) {
    $scope.userID = $routeParams.id;
    $http({
        method : "GET",
        url : "http://localhost:8080/api/user/" + $scope.userID
    }).then( function(response) {
        $scope.user = response.data;
    });
    $scope.submitUserForm = function() {
        $http({
            method : "POST",
            url : "http://localhost:8080/api/user/",
            data : $scope.user
        }).then( function(response) {
            $location.path("/list-all-users");
            $route.reload();
        }, function(errResponse) {
            $scope.errorMessage = "Error while updating user - Error message: " + errResponse.data.errorMessage;
        });
    }
    $scope.cancelUserForm = function() {
        $location.path("/list-all-users");
        $route.reload();
    }
});

myShoppingApp.controller('editItemController', function($scope, $routeParams, $route, $location, $http) {
    $scope.userID = $routeParams.id;
        $http({
            method : "GET",
            url : "http://localhost:8080/api/item/" + $scope.userID
        }).then( function(response) {
            $scope.item = response.data;
        });
    $scope.submitItemForm = function() {
        $http({
            method : "POST",
            url : "http://localhost:8080/api/item/",
            data: $scope.item
        }).then( function(response) {
            $location.path("/list-all-items");
            $route.reload();
        }, function(errResponse) {
            $scope.errorMessage = "Error while updating item. Error message: " + errResponse.data.errorMessage;
        });
    }
    $scope.cancelItemForm = function()
    {
        $location.path("/list-all-items");
        $route.reload();
    }
});