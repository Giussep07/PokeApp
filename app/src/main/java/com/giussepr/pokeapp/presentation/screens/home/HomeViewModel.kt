package com.giussepr.pokeapp.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giussepr.pokeapp.domain.model.ListViewType
import com.giussepr.pokeapp.domain.model.pokemon.Pokemon
import com.giussepr.pokeapp.domain.model.fold
import com.giussepr.pokeapp.domain.usecase.GetPokemonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    fun onLoadPokemons() {
        getPokemonsUseCase().map { result ->
            result.fold(
                onSuccess = { pokemons ->
                    uiState = uiState.copy(pokemons = pokemons, isLoading = false)
                },
                onFailure = {
                    uiState = uiState.copy(errorMessage = it.message, isLoading = false)
                })
        }.onStart { uiState = uiState.copy(isLoading = true) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun ListViewType.change() = when (this) {
        ListViewType.LIST -> ListViewType.GRID
        ListViewType.GRID -> ListViewType.LIST
    }

    fun onUiEvent(uiEvent: HomeUiEvent) {
        when (uiEvent) {
            is HomeUiEvent.LoadPokemons -> {
                onLoadPokemons()
            }
            is HomeUiEvent.ChangeListViewType -> {
                uiState = uiState.copy(listViewType = uiState.listViewType.change())
            }
        }
    }

    data class HomeUiState(
        val isLoading: Boolean = false,
        val pokemons: List<Pokemon> = emptyList(),
        val errorMessage: String = "",
        val listViewType: ListViewType = ListViewType.LIST
    )

    sealed class HomeUiEvent {
        object LoadPokemons : HomeUiEvent()
        object ChangeListViewType : HomeUiEvent()
    }
}
