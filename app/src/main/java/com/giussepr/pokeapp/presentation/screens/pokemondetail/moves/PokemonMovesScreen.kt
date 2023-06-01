package com.giussepr.pokeapp.presentation.screens.pokemondetail.moves

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giussepr.pokeapp.domain.model.moves.PokemonMove
import com.giussepr.pokeapp.presentation.theme.PokeAppTheme

@Preview(showBackground = true)
@Composable
fun PokemonMovesScreenPreview() {
    PokeAppTheme {
        PokemonMovesScreen(
            pokemonId = 1,
            uiState = PokemonMovesViewModel.PokemonMovesUiState(
                moves = listOf(
                    PokemonMove(
                        id = 1,
                        accuracy = 100,
                        power = 80,
                        pp = 10,
                        name = "Dual Wingbeat",
                        damageClass = "physical",
                        effect = "Inflicts regular damage.",
                        type = "flying"
                    ),
                    PokemonMove(
                        id = 2,
                        accuracy = 100,
                        power = 80,
                        pp = 10,
                        name = "Scorching Sands",
                        damageClass = "special",
                        effect = "Inflicts regular damage.",
                        type = "ground"
                    ),
                    PokemonMove(
                        id = 3,
                        accuracy = 100,
                        power = 80,
                        pp = 10,
                        name = "Tera Blast",
                        damageClass = "special",
                        effect = "",
                        type = "normal"
                    ),
                )
            ),
            onUiEvent = {}
        )
    }
}

@Composable
fun PokemonMovesScreen(
    pokemonId: Int,
    uiState: PokemonMovesViewModel.PokemonMovesUiState,
    onUiEvent: (PokemonMovesViewModel.PokemonMovesUiEvent) -> Unit
) {
    LaunchedEffect(key1 = true) {
        onUiEvent(PokemonMovesViewModel.PokemonMovesUiEvent.GetPokemonMoves(pokemonId))
    }

    PokemonMovesContent(uiState)
}

@Composable
fun PokemonMovesContent(
    uiState: PokemonMovesViewModel.PokemonMovesUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (uiState.isLoading.not() && uiState.moves.isNotEmpty()) {
            PokemonMovesList(moves = uiState.moves)
        }
    }
}

@Composable
fun PokemonMovesList(moves: List<PokemonMove>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(moves) { move ->
            PokemonMoveItem(move = move)
            //Divider()
        }
    }
}

@Composable
fun PokemonMoveItem(move: PokemonMove) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = move.name,
            modifier = Modifier
                .weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        Image(
            painter = painterResource(id = move.getTypeIcon()),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}
