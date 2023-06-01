package com.giussepr.pokeapp.data.mapper

import com.giussepr.pokeapp.domain.model.about.PokemonAbility
import com.giussepr.pokeapp.domain.model.about.PokemonAbout
import com.giussepr.pokeapp.domain.model.about.PokemonEggGroup
import com.giussepr.pokeserver.PokemonAboutQuery

fun PokemonAboutQuery.Pokemon_v2_egggroup.mapToDomainModel() =
    PokemonEggGroup(
        name = this.name.replaceFirstChar { it.titlecase() }
    )


fun PokemonAboutQuery.Pokemon_v2_ability.mapToDomainModel() =
    PokemonAbility(
        id = this.id,
        name = this.name.replaceFirstChar { it.titlecase() }
    )

fun PokemonAboutQuery.Pokemon_v2_pokemon.mapToDomainModel() = PokemonAbout(
    id = this.id,
    name = this.name.replaceFirstChar { it.titlecase() }.replace("-", " "),
    height = this.height ?: 0,
    weight = this.weight ?: 0,
    genderRate = this.pokemon_v2_pokemonspecy?.gender_rate ?: 0,
    hatchCounter = this.pokemon_v2_pokemonspecy?.hatch_counter ?: 0,
    abilities = this.pokemon_v2_pokemonabilities.mapNotNull { it.pokemon_v2_ability?.mapToDomainModel() },
    eggGroups = this.pokemon_v2_pokemonspecy?.pokemon_v2_pokemonegggroups?.mapNotNull { it.pokemon_v2_egggroup?.mapToDomainModel() } ?: emptyList()
)
