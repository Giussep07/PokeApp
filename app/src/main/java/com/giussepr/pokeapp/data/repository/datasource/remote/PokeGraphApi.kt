package com.giussepr.pokeapp.data.repository.datasource.remote

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.giussepr.pokeserver.PokemonAboutQuery
import com.giussepr.pokeserver.PokemonListQuery
import com.giussepr.pokeserver.PokemonMovesQuery
import com.giussepr.pokeserver.PokemonStatsQuery
import javax.inject.Inject

class PokeGraphApi @Inject constructor(private val apolloClient: ApolloClient) {

    fun queryPokemonList(): ApolloCall<PokemonListQuery.Data> {
        return apolloClient.query(PokemonListQuery())
    }

    fun queryPokemonAbout(pokemonId: Int): ApolloCall<PokemonAboutQuery.Data> {
        return apolloClient.query(
            PokemonAboutQuery(pokemon_id = pokemonId)
        )
    }

    fun queryPokemonStats(pokemonId: Int): ApolloCall<PokemonStatsQuery.Data> {
        return apolloClient.query(
            PokemonStatsQuery(pokemon_id = pokemonId)
        )
    }

    fun queryPokemonMoves(pokemonId: Int): ApolloCall<PokemonMovesQuery.Data> {
        return apolloClient.query(
            PokemonMovesQuery(pokemon_id = pokemonId)
        )
    }
}
