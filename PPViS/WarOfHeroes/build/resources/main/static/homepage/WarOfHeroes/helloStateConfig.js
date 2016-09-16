angular.module("WarOfHeroes")
    .config(function ($stateProvider) {
        $stateProvider
            .state("home", {
                url: "/",
                views: {
                    "":{
                        templateUrl: "homepage/WarOfHeroes/home.html",
                        controller: "homeController",
                        controllerAs: "vm"
                    }
                }
            })
    })