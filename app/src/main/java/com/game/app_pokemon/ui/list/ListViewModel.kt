package com.game.app_pokemon.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.app_pokemon.data.remote.PokeApiClient
import com.game.app_pokemon.domain.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private val _state = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val state: StateFlow<ListUiState> = _state

    init { load() }

    private fun load() {
        viewModelScope.launch {
            try {
                val response = PokeApiClient.api.getlistPokemons(limit = 151)
                val pokemons = response.results.map { dto ->
                    val id = dto.url.trimEnd('/').substringAfterLast('/').toInt()
                    Pokemon(id = id, name = dto.name)
                }
                _state.value = ListUiState.Success(pokemons )
            } catch (e: Exception) {
                _state.value = ListUiState.Error("Não foi possível carregar")
            }
        }
    }
}