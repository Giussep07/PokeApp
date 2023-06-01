package com.giussepr.pokeapp.presentation.screens.pokemondetail.about

import com.giussepr.pokeapp.domain.model.DomainException
import com.giussepr.pokeapp.domain.model.Result
import com.giussepr.pokeapp.domain.model.about.PokemonAbout
import com.giussepr.pokeapp.domain.usecase.GetPokemonAboutUseCase
import com.giussepr.pokeapp.utils.DispatcherProvider
import com.giussepr.pokeapp.utils.TestDispatcherProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
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
class PokemonAboutViewModelTest {

    private lateinit var pokemonAboutViewModel: PokemonAboutViewModel
    private lateinit var dispatcherProvider: DispatcherProvider

    private val getPokemonAboutUseCase: GetPokemonAboutUseCase = mockk()

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()

        pokemonAboutViewModel = PokemonAboutViewModel(
            getPokemonAboutUseCase = getPokemonAboutUseCase,
            dispatcherProvider = dispatcherProvider
        )

        Dispatchers.setMain(dispatcherProvider.main)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test onLoadPokemonAbout() success`() {
        runTest {
            // Prepare
            val expected = PokemonAbout(
                id = 1,
                name = "Pokemon 1",
                height = 1,
                weight = 1,
                genderRate = 1,
                hatchCounter = 1
            )

            every { getPokemonAboutUseCase(pokemonId = 1) } returns flowOf(Result.Success(expected))

            // Action
            pokemonAboutViewModel.onLoadPokemonAbout(pokemonId = 1)

            // Assert
            TestCase.assertEquals(expected, pokemonAboutViewModel.uiState.pokemonAbout)
        }
    }

    @Test
    fun `test onLoadPokemonAbout() error`() {
        runTest {
            // Prepare
            val expected = "Error"

            every { getPokemonAboutUseCase(pokemonId = 1) } returns flowOf(
                Result.Error(
                    DomainException("Error")
                )
            )

            // Action
            pokemonAboutViewModel.onLoadPokemonAbout(pokemonId = 1)

            // Assert
            TestCase.assertEquals(expected, pokemonAboutViewModel.uiState.errorMessage)
        }
    }

    @Test
    fun `test onUiEvent() calls onLoadPokemonAbout() once`() {
        runTest {
            // Prepare
            val expected = PokemonAbout(
                id = 1,
                name = "Pokemon 1",
                height = 1,
                weight = 1,
                genderRate = 1,
                hatchCounter = 1
            )

            every { getPokemonAboutUseCase(pokemonId = 1) } returns flowOf(Result.Success(expected))

            // Action
            pokemonAboutViewModel.onUiEvent(
                PokemonAboutViewModel.PokemonAboutUiEvent.LoadPokemonAbout(
                    pokemonId = 1
                )
            )

            // Assert
            verify(exactly = 1) { getPokemonAboutUseCase(pokemonId = 1) }
        }
    }
}
