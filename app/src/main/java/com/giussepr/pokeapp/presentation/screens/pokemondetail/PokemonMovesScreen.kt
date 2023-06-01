package com.giussepr.pokeapp.presentation.screens.pokemondetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.giussepr.pokeapp.presentation.theme.PokeAppTheme

@Preview
@Composable
fun PokemonMovesScreenPreview() {
    PokeAppTheme {
        PokemonMovesScreen()
    }
}

@Composable
fun PokemonMovesScreen() {
    Column {
        Text(text = "Moves")
    }
}
