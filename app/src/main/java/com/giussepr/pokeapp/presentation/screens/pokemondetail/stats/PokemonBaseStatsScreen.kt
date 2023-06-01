package com.giussepr.pokeapp.presentation.screens.pokemondetail.stats

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giussepr.pokeapp.domain.model.pokemon.Pokemon
import com.giussepr.pokeapp.domain.model.pokemon.PokemonType
import com.giussepr.pokeapp.domain.model.pokemon.PokemonTypeAsset
import com.giussepr.pokeapp.domain.model.stats.PokemonStat
import com.giussepr.pokeapp.presentation.theme.PokeAppTheme
import com.giussepr.pokeapp.presentation.widgets.ErrorMessage
import kotlinx.coroutines.delay
import kotlin.random.Random

@Preview(showBackground = true)
@Composable
fun PokemonBaseStatsScreenPreview() {
    PokeAppTheme {
        PokemonBaseStatsScreen(
            pokemon = Pokemon(
                1,
                "Bulbasaur",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                listOf(
                    PokemonType(1, "Grass", PokemonTypeAsset.Grass),
                    PokemonType(2, "Poison", PokemonTypeAsset.Poison)
                )
            ),
            uiState = PokemonBaseStatsViewModel.PokemonBaseStatsUiState(
                pokemonBaseStats = listOf(
                    PokemonStat(
                        name = "HP",
                        value = 45
                    ),
                    PokemonStat(
                        name = "Attack",
                        value = 49
                    ),
                    PokemonStat(
                        name = "Defense",
                        value = 49
                    ),
                    PokemonStat(
                        name = "Special Attack",
                        value = 65
                    ),
                    PokemonStat(
                        name = "Special Defense",
                        value = 65
                    ),
                    PokemonStat(
                        name = "Speed",
                        value = 45
                    ),
                )
            )
        ) {}
    }
}

@Composable
fun PokemonBaseStatsScreen(
    pokemon: Pokemon?,
    uiState: PokemonBaseStatsViewModel.PokemonBaseStatsUiState,
    onUiEvent: (PokemonBaseStatsViewModel.PokemonBaseStatsUiEvent) -> Unit
) {
    LaunchedEffect(key1 = true) {
        onUiEvent(
            PokemonBaseStatsViewModel.PokemonBaseStatsUiEvent.GetPokemonBaseStats(
                pokemonId = pokemon?.id ?: -1
            )
        )
    }
    PokemonBaseStatsContent(pokemon = pokemon, uiState = uiState)
}

@Composable
fun PokemonBaseStatsContent(
    pokemon: Pokemon?,
    uiState: PokemonBaseStatsViewModel.PokemonBaseStatsUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (uiState.isLoading.not() && uiState.pokemonBaseStats.isNotEmpty() && uiState.error.isEmpty()) {
            PokemonBaseStatsList(
                pokemon = pokemon,
                pokemonBaseStats = uiState.pokemonBaseStats
            )
        }

        if (uiState.isLoading.not() && uiState.error.isEmpty().not()) {
            ErrorMessage(error = uiState.error)
        }
    }
}

@Composable
fun PokemonBaseStatsList(pokemon: Pokemon?, pokemonBaseStats: List<PokemonStat>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(pokemonBaseStats) { pokemonStat ->
            PokemonBaseStatsItem(
                pokemon = pokemon,
                pokemonStat = pokemonStat
            )
        }
    }
}

@Composable
fun PokemonBaseStatsItem(pokemon: Pokemon?, pokemonStat: PokemonStat) {
    var progress by remember { mutableStateOf(0f) }

    val typeColors = buildList {
        if (pokemon?.types?.isNotEmpty() == true) {
            pokemon.types.forEach {
                add(it.asset.typeColor)
                add(it.asset.backgroundColor)
            }
        } else {
            add(MaterialTheme.colorScheme.primary)
            add(MaterialTheme.colorScheme.inversePrimary)
        }
    }
    val random = Random.nextInt(typeColors.size)
    val progressColor by remember { mutableStateOf(typeColors[random]) }

    val progressFloatValue: Float by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = pokemonStat.name,
            modifier = Modifier
                .weight(0.4f),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        Text(
            text = pokemonStat.value.toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(end = 16.dp)
        )
        LinearProgressIndicator(
            progress = progressFloatValue,
            modifier = Modifier.weight(1f),
            color = progressColor
        )
    }

    LaunchedEffect(key1 = true) {
        delay(100)
        progress = (pokemonStat.value.times(1)) / 255f
    }
}
