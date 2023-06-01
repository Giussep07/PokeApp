package com.giussepr.pokeapp.domain.model.about

data class PokemonAbout(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val genderRate: Int,
    val hatchCounter: Int,
    val abilities: List<PokemonAbility> = emptyList(),
    val eggGroups: List<PokemonEggGroup> = emptyList()
)

data class PokemonAbility(
    val id: Int,
    val name: String
)

data class PokemonEggGroup(
    val name: String
)
