package com.game.app_pokemon.ui.list

import com.game.app_pokemon.domain.Pokemon

// Todas as situações possíveis da tela de lista. 'sealed' = lista fechada: não existe um quarto estado,
// e o compilador avisa se o 'when' esquecer algum.
sealed interface ListUiState {
    // Buscando na internet; não precisa de dados.
    data object Loading : ListUiState
    // Deu certo; carrega a lista pronta para a tela.
    data class Success(val pokemons: List<Pokemon>) : ListUiState
    // Deu errado; carrega a mensagem para mostrar ao usuário.
    data class Error(val message: String) : ListUiState
}