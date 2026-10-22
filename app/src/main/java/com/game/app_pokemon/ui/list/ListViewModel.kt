package com.game.app_pokemon.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.app_pokemon.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Dono do estado da tela de lista: pede os dados ao Repository e publica o estado. Sobrevive a rotação de tela.
// O repository chega pelo construtor; quem monta o ViewModel é a Factory (AppViewModelProvider).
class ListViewModel(
    private val repository: PokemonRepository,

    ) : ViewModel() {
    // Versão privada e mutável: só o ViewModel pode escrever o estado. Começa em Loading.
    private val _state = MutableStateFlow<ListUiState>(ListUiState.Loading)
    // Versão pública e somente leitura: a tela só observa (fluxo de dados em uma direção).
    val state: StateFlow<ListUiState> = _state

    // Assim que o ViewModel é criado, já começa a carregar a lista.
    init {
        load()
    }

    // Chamada pela tela quando o usuário toca em "Tentar de novo".
    // Volta para Loading antes: a tela troca na hora e o botão some, evitando toques repetidos e downloads duplicados.
    fun retry() {
        _state.value = ListUiState.Loading
        load()
    }

    private fun load() {
        // Corrotina presa ao ViewModel (cancela sozinha quando ele for destruído); permite chamar funções suspend.
        viewModelScope.launch {
            try {
                // O Repository decide se baixa da API (1ª vez) ou lê do banco; o ViewModel não precisa saber.
                val pokemons = repository.getPokemons()
                _state.value = ListUiState.Success(pokemons)
            // Sem internet no 1º uso (banco vazio) ou erro: vira estado de erro em vez de fechar o app.
            } catch (e: Exception) {
                _state.value = ListUiState.Error("Não foi possível carregar")
            }
        }
    }
}
