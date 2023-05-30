package com.giussepr.pokeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.giussepr.pokeapp.presentation.screens.home.HomeScreen
import com.giussepr.pokeapp.presentation.screens.splash.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController, startDestination: String = AppScreens.SplashScreen.route) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(AppScreens.Home.route) {
            HomeScreen(navController)
        }
    }
}
