package com.giussepr.pokeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.giussepr.pokeapp.presentation.screens.home.HomeScreen
import com.giussepr.pokeapp.presentation.screens.home.HomeViewModel
import com.giussepr.pokeapp.presentation.screens.pokemondetail.PokemonDetailScreen
import com.giussepr.pokeapp.presentation.screens.pokemondetail.PokemonDetailSharedViewModel
import com.giussepr.pokeapp.presentation.screens.splash.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = AppScreens.SplashScreen.route
) {
    val pokemonDetailSharedViewModel: PokemonDetailSharedViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(AppScreens.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                uiState = homeViewModel.uiState,
                onUiEvent = homeViewModel::onUiEvent
            ) { pokemon ->
                pokemonDetailSharedViewModel.addPokemonDetail(pokemon)
                navController.navigate(AppScreens.PokemonDetail.route)
            }
        }
        composable(AppScreens.PokemonDetail.route) {
            PokemonDetailScreen(
                pokemonDetailSharedViewModel.pokemonDetail,
                onNavigateUp = navController::navigateUp
            )
        }
    }
}
