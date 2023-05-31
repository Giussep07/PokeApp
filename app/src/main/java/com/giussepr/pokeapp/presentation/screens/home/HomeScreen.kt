@file:OptIn(ExperimentalMaterial3Api::class)

package com.giussepr.pokeapp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giussepr.pokeapp.domain.model.ListViewType
import com.giussepr.pokeapp.domain.model.pokemon.Pokemon
import com.giussepr.pokeapp.domain.model.pokemon.PokemonType
import com.giussepr.pokeapp.domain.model.pokemon.PokemonTypeAsset
import com.giussepr.pokeapp.presentation.widgets.ListTypeIconButton
import com.giussepr.pokeapp.presentation.widgets.PokeAppTopAppBar
import com.giussepr.pokeapp.presentation.widgets.PokemonCardItem

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(uiState = HomeViewModel.HomeUiState(
        pokemons = listOf(
            Pokemon(
                1,
                "Bulbasaur",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                listOf(PokemonType(1, "Grass", PokemonTypeAsset.Grass))
            ),
            Pokemon(
                2,
                "Ivysaur",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
                listOf(PokemonType(2, "Poison", PokemonTypeAsset.Poison))
            ),
            Pokemon(
                3,
                "Venusaur",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
                listOf(PokemonType(2, "dragon", PokemonTypeAsset.Dragon))
            ),
            Pokemon(
                4,
                "Charmander",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png",
                listOf(PokemonType(2, "ice", PokemonTypeAsset.Ice))
            ),
            Pokemon(
                5,
                "Charmeleon",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png",
                listOf(PokemonType(2, "electric", PokemonTypeAsset.Electric))
            )
        )
    ), onUiEvent = {}, onNavigateToDetails = {})
}

@Composable
fun HomeScreen(
    uiState: HomeViewModel.HomeUiState,
    onUiEvent: (HomeViewModel.HomeUiEvent) -> Unit,
    onNavigateToDetails: (Pokemon) -> Unit
) {
    LaunchedEffect(key1 = true) {
        onUiEvent(HomeViewModel.HomeUiEvent.LoadPokemons)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PokeAppTopAppBar(actions = {
                ListTypeIconButton(onListTypeClicked = {
                    onUiEvent(HomeViewModel.HomeUiEvent.ChangeListViewType)
                })
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            if (!uiState.isLoading) {
                HomeScreenContent(uiState, uiState.listViewType, onNavigateToDetails)
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    uiState: HomeViewModel.HomeUiState,
    listViewType: ListViewType,
    onNavigateToDetails: (Pokemon) -> Unit
) {
    val gridCells = if (listViewType == ListViewType.LIST) {
        GridCells.Fixed(1)
    } else {
        GridCells.Fixed(2)
    }
    LazyVerticalGrid(
        columns = gridCells,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(uiState.pokemons) { pokemon ->
            PokemonCardItem(
                pokemon = pokemon,
                listViewType = listViewType,
                onCardClicked = onNavigateToDetails
            )
        }
    }
}
