package com.giussepr.pokeapp.data.mapper

import com.giussepr.pokeapp.domain.model.pokemon.Pokemon
import com.giussepr.pokeapp.domain.model.pokemon.PokemonType
import com.giussepr.pokeapp.domain.model.pokemon.PokemonTypeAsset
import com.giussepr.pokeserver.PokemonListQuery

private fun PokemonListQuery.Pokemon_v2_pokemon.getImageUrl(): String {
    val baseImageUrl =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

    return "$baseImageUrl${this.id}.png"
}

private fun PokemonListQuery.Pokemon_v2_pokemontype.mapToDomainModel() =
    PokemonType(
        id = this.pokemon_v2_type?.id ?: 0,
        name = this.pokemon_v2_type?.name?.replaceFirstChar { it.titlecase() } ?: "",
        asset = PokemonTypeAsset.getAsset(this.pokemon_v2_type?.name ?: "")
    )

fun PokemonListQuery.Pokemon_v2_pokemon.mapToDomainModel() =
    Pokemon(
        id = this.id,
        name = this.name.replaceFirstChar { it.titlecase() }.replace("-", " "),
        imageUrl = this.getImageUrl(),
        types = this.pokemon_v2_pokemontypes.map { it.mapToDomainModel() }
    )
