@file:OptIn(ExperimentalCoroutinesApi::class)

package com.giussepr.pokeapp.presentation.screens.home

import com.giussepr.pokeapp.domain.model.DomainException
import com.giussepr.pokeapp.domain.model.Result
import com.giussepr.pokeapp.domain.model.pokemon.Pokemon
import com.giussepr.pokeapp.domain.usecase.GetPokemonsUseCase
import com.giussepr.pokeapp.utils.DispatcherProvider
import com.giussepr.pokeapp.utils.TestDispatcherProvider
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var dispatcherProvider: DispatcherProvider

    private val getPokemonsUseCase: GetPokemonsUseCase = mockk()

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()

        homeViewModel = HomeViewModel(
            getPokemonsUseCase = getPokemonsUseCase,
            dispatcherProvider = dispatcherProvider
        )

        Dispatchers.setMain(dispatcherProvider.main)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test onLoadPokemons() success`() {
        runTest {
            // Prepare
            val expected = buildList {
                add(Pokemon(id = 1, name = "Pokemon 1", imageUrl = null, types = emptyList()))
                add(Pokemon(id = 2, name = "Pokemon 2", imageUrl = null, types = emptyList()))
                add(Pokemon(id = 3, name = "Pokemon 3", imageUrl = null, types = emptyList()))
            }

            every { getPokemonsUseCase() } returns flowOf(Result.Success(expected))

            // Action
            homeViewModel.onLoadPokemons()

            // Assert
            assertEquals(expected, homeViewModel.uiState.pokemons)
        }
    }

    @Test
    fun `test onLoadPokemons() error`() {
        runTest {
            // Prepare
            val expected = "Error"

            every { getPokemonsUseCase() } returns flowOf(Result.Error(DomainException("Error")))

            // Execute
            homeViewModel.onLoadPokemons()

            // Verify
            Assert.assertEquals(homeViewModel.uiState.errorMessage, expected)
        }
    }

}
