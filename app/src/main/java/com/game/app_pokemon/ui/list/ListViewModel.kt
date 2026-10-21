package com.game.app_pokemon.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.app_pokemon.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: PokemonRepository,

): ViewModel() {
    private val _state = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val state: StateFlow<ListUiState> = _state
    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            try {
                val pokemons = repository.getPokemons()
                _state.value = ListUiState.Success(pokemons)
            } catch (e: Exception) {
                _state.value = ListUiState.Error("Não foi possível carregar")
            }
        }
    }
}
