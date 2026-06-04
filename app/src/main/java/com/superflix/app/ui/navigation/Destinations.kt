// ui/navigation/Destinations.kt
package com.superflix.app.ui.navigation

sealed class Destinations(val route: String) {
    object Home : Destinations("home")
    object Search : Destinations("search")
    object Library : Destinations("library")
    object Profile : Destinations("profile")
    object Details : Destinations("details/{movieId}/{type}")
    object Player : Destinations("player/{movieId}")
    
    fun passArguments(vararg args: String): String {
        var route = this.route
        args.forEach { arg ->
            route = route.replaceFirst("\\{.*?\\}", arg)
        }
        return route
    }
}