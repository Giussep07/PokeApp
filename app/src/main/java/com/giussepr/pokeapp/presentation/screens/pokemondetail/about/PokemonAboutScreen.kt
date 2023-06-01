package com.giussepr.pokeapp.presentation.screens.pokemondetail.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giussepr.pokeapp.R
import com.giussepr.pokeapp.domain.model.about.PokemonAbility
import com.giussepr.pokeapp.domain.model.about.PokemonAbout
import com.giussepr.pokeapp.domain.model.about.PokemonEggGroup
import com.giussepr.pokeapp.presentation.theme.PokeAppTheme
import com.giussepr.pokeapp.presentation.widgets.ErrorMessage
import com.giussepr.pokeapp.presentation.widgets.PokemonDataItemDefaults
import com.giussepr.pokeapp.presentation.widgets.PokemonDataItemTitle

@Preview(showBackground = true)
@Composable
fun PokemonAboutScreenPreview() {
    PokeAppTheme {
        PokemonAboutScreen(
            pokemonId = 1,
            uiState = PokemonAboutViewModel.PokemonAboutUiState(
                pokemonAbout = PokemonAbout(
                    id = 1,
                    name = "Balbasaur",
                    height = 70,
                    weight = 10,
                    genderRate = 1,
                    hatchCounter = 20,
                    abilities = listOf(
                        PokemonAbility(
                            id = 1,
                            name = "Ability 1"
                        ),
                        PokemonAbility(
                            id = 2,
                            name = "Ability 2"
                        ),
                        PokemonAbility(
                            id = 3,
                            name = "Ability 3"
                        )
                    ),
                    eggGroups = listOf(
                        PokemonEggGroup(
                            name = "Egg Group 1"
                        ),
                        PokemonEggGroup(
                            name = "Egg Group 2"
                        ),
                    ),
                )
            ),
            onUiEvent = {}
        )
    }
}

@Composable
fun PokemonAboutScreen(
    pokemonId: Int,
    uiState: PokemonAboutViewModel.PokemonAboutUiState,
    onUiEvent: (PokemonAboutViewModel.PokemonAboutUiEvent) -> Unit
) {

    LaunchedEffect(true) {
        onUiEvent(PokemonAboutViewModel.PokemonAboutUiEvent.LoadPokemonAbout(pokemonId = pokemonId))
    }

    PokemonAboutContent(uiState = uiState)
}

@Composable
fun PokemonAboutContent(uiState: PokemonAboutViewModel.PokemonAboutUiState) {
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


        if (uiState.isLoading.not() && uiState.pokemonAbout != null && uiState.errorMessage.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Height
                PokemonDataItemDefaults.PokemonDataItem(
                    title = stringResource(R.string.height),
                    value = "${uiState.pokemonAbout.height.times(10)} cm"
                )
                // Weight
                PokemonDataItemDefaults.PokemonDataItem(
                    title = stringResource(R.string.weight),
                    value = "${uiState.pokemonAbout.weight.div(10)} Kg"
                )
                // Abilities
                PokemonDataItemDefaults.PokemonDataItem(
                    title = stringResource(R.string.abilities),
                    value = uiState.pokemonAbout.abilities.joinToString { it.name }
                )
            }
            // Breeding Title
            PokemonDataItemTitle(title = stringResource(R.string.breeding))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Gender
                PokemonDataItemDefaults.PokemonDataItemGender(genderRate = uiState.pokemonAbout.genderRate)
                // Egg Groups
                PokemonDataItemDefaults.PokemonDataItem(
                    title = stringResource(R.string.egg_groups),
                    value = uiState.pokemonAbout.eggGroups.joinToString { it.name }
                )
                // Egg Cycles
                PokemonDataItemDefaults.PokemonDataItem(
                    title = stringResource(R.string.egg_cycles),
                    value = "${(uiState.pokemonAbout.hatchCounter.plus(1).times(255))} steps"
                )
            }
        }

        if (uiState.isLoading.not() && uiState.errorMessage.isEmpty().not()) {
            ErrorMessage(error = uiState.errorMessage)
        }
    }
}
