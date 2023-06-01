package com.giussepr.pokeapp.presentation.screens.pokemondetail.stats

import com.giussepr.pokeapp.domain.model.DomainException
import com.giussepr.pokeapp.domain.model.Result
import com.giussepr.pokeapp.domain.model.stats.PokemonStat
import com.giussepr.pokeapp.domain.usecase.GetPokemonStatsUseCase
import com.giussepr.pokeapp.utils.DispatcherProvider
import com.giussepr.pokeapp.utils.TestDispatcherProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonBaseStatsViewModelTest {

    private lateinit var pokemonBaseStatsViewModel: PokemonBaseStatsViewModel
    private lateinit var dispatcherProvider: DispatcherProvider

    private val getPokemonStatsUseCase: GetPokemonStatsUseCase = mockk()

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()

        pokemonBaseStatsViewModel = PokemonBaseStatsViewModel(
            getPokemonStatsUseCase = getPokemonStatsUseCase,
            dispatcherProvider = dispatcherProvider
        )

        Dispatchers.setMain(dispatcherProvider.main)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getPokemonBaseStats() success`() {
        runTest {
            // Prepare
            val expected = buildList {
                add(PokemonStat("hp", 45))
                add(PokemonStat("attack", 49))
                add(PokemonStat("defense", 49))
                add(PokemonStat("special-attack", 65))
                add(PokemonStat("special-defense", 65))
                add(PokemonStat("speed", 45))
            }

            every { getPokemonStatsUseCase.invoke(any()) } returns flowOf(Result.Success(expected))

            // Execute
            pokemonBaseStatsViewModel.getPokemonBaseStats(pokemonId = 1)

            // Verify
            verify(exactly = 1) { getPokemonStatsUseCase.invoke(any()) }

            // Assert
            assertEquals(expected, pokemonBaseStatsViewModel.uiState.pokemonBaseStats)
        }
    }

    @Test
    fun `test getPokemonBaseStats() error`() {
        runTest {
            // Prepare
            val expected = "Error"

            every { getPokemonStatsUseCase(pokemonId = 1) } returns flowOf(
                Result.Error(DomainException("Error"))
            )

            // Execute
            pokemonBaseStatsViewModel.getPokemonBaseStats(pokemonId = 1)

            // Verify
            verify(exactly = 1) { getPokemonStatsUseCase.invoke(any()) }

            // Assert
            assertEquals(expected, pokemonBaseStatsViewModel.uiState.error)
        }
    }

}
