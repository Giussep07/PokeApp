package com.giussepr.pokeapp.presentation.navigation

sealed class AppScreens(val route: String) {
    object SplashScreen: AppScreens("splash_screen")
    object Home: AppScreens("home_screen")
}
