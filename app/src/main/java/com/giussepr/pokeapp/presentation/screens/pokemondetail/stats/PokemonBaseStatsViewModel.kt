package com.giussepr.pokeapp.presentation.screens.pokemondetail.stats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giussepr.pokeapp.domain.model.fold
import com.giussepr.pokeapp.domain.model.stats.PokemonStat
import com.giussepr.pokeapp.domain.usecase.GetPokemonStatsUseCase
import com.giussepr.pokeapp.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PokemonBaseStatsViewModel @Inject constructor(
    private val getPokemonStatsUseCase: GetPokemonStatsUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var uiState by mutableStateOf(PokemonBaseStatsUiState())
        private set

    fun getPokemonBaseStats(pokemonId: Int) {
        getPokemonStatsUseCase.invoke(pokemonId).map { result ->
            result.fold(
                onSuccess = { pokemonStats ->
                    uiState = uiState.copy(pokemonBaseStats = pokemonStats, isLoading = false)
                },
                onFailure = {
                    uiState = uiState.copy(error = it.message, isLoading = false)
                }
            )

        }.onStart { uiState = uiState.copy(isLoading = true) }
            .flowOn(dispatcherProvider.io)
            .launchIn(viewModelScope)
    }

    fun onUiEvent(uiEvent: PokemonBaseStatsUiEvent) {
        when (uiEvent) {
            is PokemonBaseStatsUiEvent.GetPokemonBaseStats -> getPokemonBaseStats(uiEvent.pokemonId)
        }
    }

    data class PokemonBaseStatsUiState(
        val isLoading: Boolean = false,
        val pokemonBaseStats: List<PokemonStat> = emptyList(),
        val error: String = ""
    )

    sealed class PokemonBaseStatsUiEvent {
        data class GetPokemonBaseStats(val pokemonId: Int) : PokemonBaseStatsUiEvent()
    }
}
