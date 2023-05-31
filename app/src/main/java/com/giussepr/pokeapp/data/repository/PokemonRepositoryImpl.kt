package com.giussepr.pokeapp.data.repository

import com.giussepr.pokeapp.data.mapper.mapToDomainModel
import com.giussepr.pokeapp.data.repository.datasource.remote.PokeGraphApi
import com.giussepr.pokeapp.domain.model.DomainException
import com.giussepr.pokeapp.domain.model.pokemon.Pokemon
import com.giussepr.pokeapp.domain.model.Result
import com.giussepr.pokeapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokeGraphApi: PokeGraphApi
) : PokemonRepository {

    override fun getPokemons(): Flow<Result<List<Pokemon>>> = flow {
        val response = pokeGraphApi.queryPokemonList().execute()

        try {
            if (response.hasErrors()) {
                emit(Result.Error(DomainException(response.errors?.get(0)?.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
            } else {
                val pokemons = response.data?.pokemon_v2_pokemon?.map { it.mapToDomainModel() } ?: emptyList()

                emit(Result.Success(pokemons))
            }
        } catch (e: Exception) {
            emit(Result.Error(DomainException(e.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
        }
    }
}
