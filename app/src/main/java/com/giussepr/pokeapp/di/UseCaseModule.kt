package com.giussepr.pokeapp.di

import com.giussepr.pokeapp.domain.repository.PokemonRepository
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
}
