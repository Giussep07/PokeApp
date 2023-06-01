package com.giussepr.pokeapp.data.repository

import com.giussepr.pokeapp.data.mapper.mapToDomainModel
import com.giussepr.pokeapp.data.repository.datasource.remote.PokeGraphApi
import com.giussepr.pokeapp.data.utils.NetworkUtils
import com.giussepr.pokeapp.domain.model.DomainException
import com.giussepr.pokeapp.domain.model.pokemon.Pokemon
import com.giussepr.pokeapp.domain.model.Result
import com.giussepr.pokeapp.domain.model.about.PokemonAbout
import com.giussepr.pokeapp.domain.model.moves.PokemonMove
import com.giussepr.pokeapp.domain.model.stats.PokemonStat
import com.giussepr.pokeapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.experimental.ExperimentalTypeInference

class PokemonRepositoryImpl @Inject constructor(
    private val pokeGraphApi: PokeGraphApi,
    private val networkUtils: NetworkUtils
) : PokemonRepository {

    override fun getPokemons(): Flow<Result<List<Pokemon>>> = fetchData {
        val response = pokeGraphApi.queryPokemonList().execute()

        if (response.hasErrors()) {
            emit(Result.Error(DomainException(response.errors?.get(0)?.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
            return@fetchData
        }

        val pokemons = response.data?.pokemon_v2_pokemon?.map { it.mapToDomainModel() } ?: emptyList()

        emit(Result.Success(pokemons))
    }.catch {
        emit(Result.Error(DomainException(it.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
    }

    override fun getPokemonAbout(pokemonId: Int): Flow<Result<PokemonAbout>> = fetchData {
        val response = pokeGraphApi.queryPokemonAbout(pokemonId).execute()

        if (response.hasErrors()) {
            emit(Result.Error(DomainException(response.errors?.get(0)?.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
            return@fetchData
        }

        val pokemonAbout = response.data?.pokemon_v2_pokemon?.get(0)?.mapToDomainModel()
            ?: throw IllegalStateException("Algo ha salido mal, por favor intenta de nuevo")

        emit(Result.Success(pokemonAbout))
    }.catch {
        emit(Result.Error(DomainException(it.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
    }

    override fun getPokemonStats(pokemonId: Int): Flow<Result<List<PokemonStat>>> = fetchData {
        val response = pokeGraphApi.queryPokemonStats(pokemonId).execute()

        if (response.hasErrors()) {
            emit(Result.Error(DomainException(response.errors?.get(0)?.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
            return@fetchData
        }

        val pokemonStats = response.data?.pokemon_v2_pokemon?.get(0)?.pokemon_v2_pokemonstats?.mapToDomainModel()
            ?: emptyList()

        emit(Result.Success(pokemonStats))
    }.catch {
        emit(Result.Error(DomainException(it.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
    }

    override fun getPokemonMoves(pokemonId: Int): Flow<Result<List<PokemonMove>>> = fetchData {
        val response = pokeGraphApi.queryPokemonMoves(pokemonId).execute()

        if (response.hasErrors()) {
            emit(Result.Error(DomainException(response.errors?.get(0)?.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
            return@fetchData
        }

        val pokemonMoves = response.data?.pokemon_v2_move?.map { it.mapToDomainModel() } ?: emptyList()

        emit(Result.Success(pokemonMoves))
    }.catch {
        emit(Result.Error(DomainException(it.message ?: "Algo ha salido mal, por favor intenta de nuevo")))
    }

    @OptIn(ExperimentalTypeInference::class)
    private fun <T> fetchData(@BuilderInference block: suspend FlowCollector<Result<T>>.() -> Unit): Flow<Result<T>> =
        flow {
            if (networkUtils.isInternetAvailable().not()) {
                emit(Result.Error(DomainException("No hay conexión a internet")))
                return@flow
            }

            block()
        }
}
