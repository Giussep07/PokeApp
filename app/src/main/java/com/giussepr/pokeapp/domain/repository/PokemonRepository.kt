package com.giussepr.pokeapp.domain.repository

import com.giussepr.pokeapp.domain.model.pokemon.Pokemon
import com.giussepr.pokeapp.domain.model.Result
import com.giussepr.pokeapp.domain.model.about.PokemonAbout
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemons(): Flow<Result<List<Pokemon>>>
    fun getPokemonAbout(pokemonId: Int): Flow<Result<PokemonAbout>>
}
