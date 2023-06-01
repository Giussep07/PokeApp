package com.giussepr.pokeapp.domain.usecase

import com.giussepr.pokeapp.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonStatsUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(pokemonId: Int) = pokemonRepository.getPokemonStats(pokemonId)
}
