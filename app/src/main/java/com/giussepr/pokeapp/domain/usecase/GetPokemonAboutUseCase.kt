package com.giussepr.pokeapp.domain.usecase

import com.giussepr.pokeapp.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonAboutUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {

    operator fun invoke(pokemonId: Int) = pokemonRepository.getPokemonAbout(pokemonId)
}
