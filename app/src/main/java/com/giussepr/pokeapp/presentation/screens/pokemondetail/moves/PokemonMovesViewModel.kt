package com.giussepr.pokeapp.presentation.screens.pokemondetail.moves

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giussepr.pokeapp.domain.model.fold
import com.giussepr.pokeapp.domain.model.moves.PokemonMove
import com.giussepr.pokeapp.domain.usecase.GetPokemonMovesUseCase
import com.giussepr.pokeapp.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PokemonMovesViewModel @Inject constructor(
    private val getPokemonMovesUseCase: GetPokemonMovesUseCase,
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {

    var uiState: PokemonMovesUiState by mutableStateOf(PokemonMovesUiState())
        private set

    fun getPokemonMoves(pokemonId: Int) {
        getPokemonMovesUseCase.invoke(pokemonId).map { result ->
            result.fold(
                onSuccess = { pokemonMoves ->
                    uiState = uiState.copy(moves = pokemonMoves, isLoading = false)
                },
                onFailure = {
                    uiState = uiState.copy(error = it.message, isLoading = false)
                }
            )

        }.onStart { uiState = uiState.copy(isLoading = true) }
            .flowOn(dispatcherProvider.io)
            .launchIn(viewModelScope)
    }

    fun onUiEvent(event: PokemonMovesUiEvent) {
        when(event) {
            is PokemonMovesUiEvent.GetPokemonMoves -> getPokemonMoves(event.pokemonId)
        }
    }

    data class PokemonMovesUiState(
        val isLoading: Boolean = false,
        val moves: List<PokemonMove> = emptyList(),
        val error: String = ""
    )

    sealed class PokemonMovesUiEvent {
        data class GetPokemonMoves(val pokemonId: Int): PokemonMovesUiEvent()
    }
}
