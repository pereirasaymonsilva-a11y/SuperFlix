package com.superflix.app.ui.navigation

sealed class Destinations(val route: String) {
    object Home : Destinations("home")
    object Search : Destinations("search")
    object Library : Destinations("library")
    object Profile : Destinations("profile")
}