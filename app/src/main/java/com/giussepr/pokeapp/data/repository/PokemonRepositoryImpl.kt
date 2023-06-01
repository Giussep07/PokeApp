package com.giussepr.pokeapp.data.repository

import com.giussepr.pokeapp.data.mapper.mapToDomainModel
import com.giussepr.pokeapp.data.repository.datasource.remote.PokeGraphApi
import com.giussepr.pokeapp.domain.model.DomainException
import com.giussepr.pokeapp.domain.model.pokemon.Pokemon
import com.giussepr.pokeapp.domain.model.Result
import com.giussepr.pokeapp.domain.model.about.PokemonAbout
import com.giussepr.pokeapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokeGraphApi: PokeGraphApi
) : PokemonRepository {

    override fun getPokemons(): Flow<Result<List<Pokemon>>> = flow {
        val response = pokeGraphApi.queryPokemonList().execute()

        if (response.hasErrors()) {
            emit(Result.Error(DomainException(response.errors?.get(0)?.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
        }

        val pokemons = response.data?.pokemon_v2_pokemon?.map { it.mapToDomainModel() } ?: emptyList()

        emit(Result.Success(pokemons))
    }.catch {
        emit(Result.Error(DomainException(it.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
    }

    override fun getPokemonAbout(pokemonId: Int): Flow<Result<PokemonAbout>> = flow<Result<PokemonAbout>> {
        val response = pokeGraphApi.queryPokemonAbout(pokemonId).execute()

        if (response.hasErrors()) {
            emit(Result.Error(DomainException(response.errors?.get(0)?.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
        }

        val pokemonAbout = response.data?.pokemon_v2_pokemon?.get(0)?.mapToDomainModel()
            ?: throw IllegalStateException("Algo ha salido mal, por favor intenta de nuevo")

        emit(Result.Success(pokemonAbout))
    }.catch {
        emit(Result.Error(DomainException(it.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
    }
}
