package com.game.app_pokemon.ui.list

import com.game.app_pokemon.domain.Pokemon

sealed interface ListUiState {
    data object Loading : ListUiState
    data class Success(val pokemons: List<Pokemon>) : ListUiState
    data class Error(val message: String) : ListUiState
}