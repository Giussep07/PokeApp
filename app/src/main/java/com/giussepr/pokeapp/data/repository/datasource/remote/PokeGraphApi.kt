package com.giussepr.pokeapp.data.repository.datasource.remote

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.giussepr.pokeserver.PokemonListQuery
import javax.inject.Inject

class PokeGraphApi @Inject constructor(private val apolloClient: ApolloClient) {

    fun queryPokemonList(): ApolloCall<PokemonListQuery.Data> {
        return apolloClient.query(PokemonListQuery())
    }
}
