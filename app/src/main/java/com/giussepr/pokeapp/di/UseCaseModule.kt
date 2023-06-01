package com.giussepr.pokeapp.di

import com.giussepr.pokeapp.domain.repository.PokemonRepository
import com.giussepr.pokeapp.domain.usecase.GetPokemonAboutUseCase
import com.giussepr.pokeapp.domain.usecase.GetPokemonStatsUseCase
import com.giussepr.pokeapp.domain.usecase.GetPokemonsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPokemonsUseCase(pokemonRepository: PokemonRepository) =
        GetPokemonsUseCase(pokemonRepository)

    @Provides
    fun provideGetPokemonAboutUseCase(pokemonRepository: PokemonRepository) =
        GetPokemonAboutUseCase(pokemonRepository)

    @Provides
    fun provideGetPokemonStatsUseCase(pokemonRepository: PokemonRepository) =
        GetPokemonStatsUseCase(pokemonRepository)
}
