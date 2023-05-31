package com.giussepr.pokeapp.presentation.screens.pokemondetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.giussepr.pokeapp.domain.model.pokemon.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailSharedViewModel @Inject constructor(): ViewModel() {

    var pokemonDetail by mutableStateOf<Pokemon?>(null)
        private set

    fun addPokemonDetail(pokemon: Pokemon) {
        this.pokemonDetail = pokemon
    }
}
