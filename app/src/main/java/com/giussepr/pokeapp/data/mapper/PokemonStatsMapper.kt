package com.giussepr.pokeapp.data.mapper

import com.giussepr.pokeapp.domain.model.stats.PokemonStat
import com.giussepr.pokeserver.PokemonStatsQuery

private fun PokemonStatsQuery.Pokemon_v2_pokemonstat?.mapToDomainModel(): PokemonStat {
    return PokemonStat(
        name = this?.pokemon_v2_stat?.pokemon_v2_statnames?.firstOrNull()?.name ?: "",
        value = this?.base_stat ?: 0
    )
}

fun List<PokemonStatsQuery.Pokemon_v2_pokemonstat>.mapToDomainModel(): List<PokemonStat> {
    return this.map { it.mapToDomainModel() }
}
