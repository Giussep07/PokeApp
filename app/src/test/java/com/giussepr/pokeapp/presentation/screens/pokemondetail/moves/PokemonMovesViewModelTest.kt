package com.giussepr.pokeapp.presentation.screens.pokemondetail.moves

import com.giussepr.pokeapp.domain.model.DomainException
import com.giussepr.pokeapp.domain.model.Result
import com.giussepr.pokeapp.domain.model.moves.PokemonMove
import com.giussepr.pokeapp.domain.usecase.GetPokemonMovesUseCase
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
class PokemonMovesViewModelTest {

    private lateinit var pokemonMovesViewModel: PokemonMovesViewModel
    private lateinit var dispatcherProvider: DispatcherProvider

    private val getPokemonMovesUseCase: GetPokemonMovesUseCase = mockk()

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()

        pokemonMovesViewModel = PokemonMovesViewModel(
            getPokemonMovesUseCase = getPokemonMovesUseCase,
            dispatcherProvider = dispatcherProvider
        )

        Dispatchers.setMain(dispatcherProvider.main)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getPokemonMoves() success`() {
        runTest {
            // Prepare
            val expected: List<PokemonMove> = buildList {
                add(PokemonMove(
                    id = 1,
                    accuracy = 100,
                    power = 80,
                    pp = 10,
                    name = "Dual Wingbeat",
                    damageClass = "physical",
                    effect = "Inflicts regular damage.",
                    type = "flying"
                ))
                add(PokemonMove(
                    id = 2,
                    accuracy = 100,
                    power = 80,
                    pp = 10,
                    name = "Scorching Sands",
                    damageClass = "special",
                    effect = "Inflicts regular damage.",
                    type = "ground"
                ))
                add(PokemonMove(
                    id = 3,
                    accuracy = 100,
                    power = 80,
                    pp = 10,
                    name = "Tera Blast",
                    damageClass = "special",
                    effect = "",
                    type = "normal"
                ))
            }

            every { getPokemonMovesUseCase(any()) } returns flowOf(Result.Success(expected))

            // Action
            pokemonMovesViewModel.getPokemonMoves(1)

            verify(exactly = 1) { getPokemonMovesUseCase(1) }

            // Assert
            assertEquals(expected, pokemonMovesViewModel.uiState.moves)
        }
    }

    @Test
    fun `test getPokemonMoves() error`() {
        runTest {
            // Prepare
            val expected = "Error"

            every { getPokemonMovesUseCase(any()) } returns flowOf(Result.Error(DomainException("Error")))

            // Action
            pokemonMovesViewModel.getPokemonMoves(1)

            // Verify
            verify(exactly = 1) { getPokemonMovesUseCase(1) }

            // Assert
            assertEquals(expected, pokemonMovesViewModel.uiState.error)
        }
    }
}
