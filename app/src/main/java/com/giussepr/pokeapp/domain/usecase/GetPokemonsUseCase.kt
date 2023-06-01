package com.giussepr.pokeapp.domain.usecase

import com.giussepr.pokeapp.domain.repository.PokemonRepository

class GetPokemonsUseCase(private val repository: PokemonRepository) {

    operator fun invoke() = repository.getPokemons()
}
