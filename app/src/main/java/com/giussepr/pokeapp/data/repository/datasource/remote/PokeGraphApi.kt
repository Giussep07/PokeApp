package com.giussepr.pokeapp.data.repository.datasource.remote

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.giussepr.pokeserver.PokemonAboutQuery
import com.giussepr.pokeserver.PokemonListQuery
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
}
