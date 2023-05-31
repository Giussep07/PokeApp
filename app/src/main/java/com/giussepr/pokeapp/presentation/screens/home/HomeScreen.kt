@file:OptIn(ExperimentalMaterial3Api::class)

package com.giussepr.pokeapp.presentation.screens.home

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giussepr.pokeapp.domain.model.ListViewType
import com.giussepr.pokeapp.presentation.widgets.PokeAppTopAppBar
import com.giussepr.pokeapp.presentation.widgets.PokemonCardItem

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    var listViewType: ListViewType by remember {
        mutableStateOf(ListViewType.LIST)
    }

    LaunchedEffect(key1 = true) {
        viewModel.onUiEvent(HomeViewModel.HomeUiEvent.LoadPokemons)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PokeAppTopAppBar(navController = navController, onListTypeClicked = {
                listViewType = if (listViewType == ListViewType.LIST)
                    ListViewType.GRID else ListViewType.LIST
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            if (viewModel.uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            if (!viewModel.uiState.isLoading) {
                HomeScreenContent(viewModel, listViewType)
            }
        }
    }
}

@Composable
fun HomeScreenContent(viewModel: HomeViewModel, listViewType: ListViewType) {
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
        items(viewModel.uiState.pokemons) { pokemon ->
            PokemonCardItem(pokemon, listViewType)
        }
    }
}
