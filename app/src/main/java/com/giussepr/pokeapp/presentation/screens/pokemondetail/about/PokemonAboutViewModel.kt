package com.giussepr.pokeapp.presentation.screens.pokemondetail.about

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giussepr.pokeapp.domain.model.about.PokemonAbout
import com.giussepr.pokeapp.domain.model.fold
import com.giussepr.pokeapp.domain.usecase.GetPokemonAboutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PokemonAboutViewModel @Inject constructor(
    private val getPokemonAboutUseCase: GetPokemonAboutUseCase
) : ViewModel() {

    var uiState by mutableStateOf(PokemonAboutUiState())
        private set

    private fun onLoadPokemonAbout(pokemonId: Int) {
        getPokemonAboutUseCase.invoke(pokemonId = pokemonId).map { result ->
            result.fold(
                onSuccess = { pokemonAbout ->
                    uiState = uiState.copy(pokemonAbout = pokemonAbout, isLoading = false)
                },
                onFailure = {
                    uiState = uiState.copy(errorMessage = it.message, isLoading = false)
                }
            )
        }.onStart { uiState = uiState.copy(isLoading = true) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun onUiEvent(uiEvent: PokemonAboutUiEvent) {
        when (uiEvent) {
            is PokemonAboutUiEvent.LoadPokemonAbout -> {
                onLoadPokemonAbout(uiEvent.pokemonId)
            }
        }
    }

    data class PokemonAboutUiState(
        val isLoading: Boolean = false,
        val pokemonAbout: PokemonAbout? = null,
        val errorMessage: String? = null
    )

    sealed class PokemonAboutUiEvent {
        data class LoadPokemonAbout(val pokemonId: Int) : PokemonAboutUiEvent()
    }
}
