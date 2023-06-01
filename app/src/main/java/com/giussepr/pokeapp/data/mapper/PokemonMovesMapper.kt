package com.giussepr.pokeapp.data.mapper

import com.giussepr.pokeapp.domain.model.moves.PokemonMove
import com.giussepr.pokeserver.PokemonMovesQuery

fun PokemonMovesQuery.Pokemon_v2_move.mapToDomainModel() = PokemonMove(
    id = this.id,
    name = this.pokemon_v2_movenames.firstOrNull()?.name ?: "",
    accuracy = this.accuracy ?: 0,
    pp = this.pp ?: 0,
    power = this.power ?: 0,
    damageClass = this.pokemon_v2_movedamageclass?.name ?: "",
    effect = this.pokemon_v2_moveeffect?.pokemon_v2_moveeffecteffecttexts?.firstOrNull()?.effect ?: "",
    type = this.pokemon_v2_type?.name ?: ""
)
