package com.giussepr.pokeapp.di

import com.apollographql.apollo3.ApolloClient
import com.giussepr.pokeapp.data.repository.PokemonRepositoryImpl
import com.giussepr.pokeapp.data.repository.datasource.remote.PokeGraphApi
import com.giussepr.pokeapp.data.utils.NetworkUtils
import com.giussepr.pokeapp.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun providePokeGraphApi(apolloClient: ApolloClient): PokeGraphApi {
        return PokeGraphApi(apolloClient)
    }

    @Provides
    fun providePokemonRepository(
        pokeGraphApi: PokeGraphApi,
        networkUtils: NetworkUtils
    ): PokemonRepository {
        return PokemonRepositoryImpl(pokeGraphApi, networkUtils)
    }
}
