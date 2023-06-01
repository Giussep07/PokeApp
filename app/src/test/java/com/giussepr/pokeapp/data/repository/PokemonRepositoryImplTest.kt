package com.giussepr.pokeapp.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.mockserver.MockResponse
import com.apollographql.apollo3.mockserver.MockServer
import com.apollographql.apollo3.mockserver.enqueue
import com.giussepr.pokeapp.data.repository.datasource.remote.PokeGraphApi
import com.giussepr.pokeapp.data.utils.NetworkUtils
import com.giussepr.pokeapp.domain.model.Result
import com.giussepr.pokeapp.domain.repository.PokemonRepository
import com.giussepr.pokeserver.PokemonListQuery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test


class PokemonRepositoryImplTest {

    private lateinit var repository: PokemonRepository

    private val graphApi: PokeGraphApi = mockk()
    private val networkUtils: NetworkUtils = mockk()
    private val mockServer = MockServer()


    @Before
    fun setUp() {
        repository = PokemonRepositoryImpl(graphApi, networkUtils)
    }

    @Test
    fun `test getPokemons() success`() {
        runTest {
            // Prepare
            every { networkUtils.isInternetAvailable() } returns true

            val apolloClient = ApolloClient.Builder()
                .serverUrl(mockServer.url())
                .build()

            mockServer.enqueue("""{"data":{"pokemon_v2_pokemon":[{"id":1,"name":"bulbasaur","pokemon_v2_pokemontypes":[{"pokemon_v2_type":{"name":"grass","id":12}},{"pokemon_v2_type":{"name":"poison","id":4}}]}]}}""")

            every { graphApi.queryPokemonList() } returns apolloClient.query(PokemonListQuery())

            // Action
            repository.getPokemons().map { result ->

                // Verify
                verify(exactly = 1) { graphApi.queryPokemonList() }
                assertEquals(
                    "bulbasaur",
                    (result as Result.Success).data.firstOrNull()?.name?.lowercase()
                )

                mockServer.stop()
            }.collect()
        }
    }

    @Test
    fun `test getPokemons() no internet connection`() {
        runTest {
            // Prepare
            every { networkUtils.isInternetAvailable() } returns false

            val apolloClient = ApolloClient.Builder()
                .serverUrl(mockServer.url())
                .build()

            every { graphApi.queryPokemonList() } returns apolloClient.query(PokemonListQuery())

            // Action
            repository.getPokemons().map { result ->

                // Verify
                verify(exactly = 0) { graphApi.queryPokemonList() }
                assertEquals(
                    "No hay conexión a internet",
                    (result as Result.Error).domainException.message
                )

                mockServer.stop()
            }.collect()
        }
    }

    @Test
    fun `test getPokemons() error`() = runTest {
        // Prepare
        every { networkUtils.isInternetAvailable() } returns true

        val apolloClient = ApolloClient.Builder()
            .serverUrl(mockServer.url())
            .build()

        mockServer.enqueue(
            MockResponse.Builder()
                .statusCode(statusCode = 400)
                .headers(
                    headers = mapOf("X-Test" to "true")
                    // Optionally pass a delay to simulate network latency
                )
                .body(body = "Internal server error")
                .build()
        )

        every { graphApi.queryPokemonList() } returns apolloClient.query(PokemonListQuery())

        // Action
        repository.getPokemons().map { result ->

            // Verify
            verify(exactly = 1) { graphApi.queryPokemonList() }
            assertNotEquals(
                "",
                (result as Result.Error).domainException.message
            )

            mockServer.stop()
        }.collect()
    }
}
