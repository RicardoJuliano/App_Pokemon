package com.game.app_pokemon.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.app_pokemon.data.remote.PokeApiClient
import com.game.app_pokemon.domain.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Dono do estado da tela de lista: busca os dados e publica o estado. Sobrevive a rotação de tela.
class ListViewModel : ViewModel() {

    // Versão privada e mutável: só o ViewModel pode escrever o estado. Começa em Loading.
    private val _state = MutableStateFlow<ListUiState>(ListUiState.Loading)
    // Versão pública e somente leitura: a tela só observa (fluxo de dados em uma direção).
    val state: StateFlow<ListUiState> = _state

    // Assim que o ViewModel é criado, já começa a carregar a lista.
    init { load() }

    private fun load() {
        // Abre uma corrotina presa ao ViewModel (cancela sozinha quando ele for destruído).
        // É ela que permite chamar a API (suspend) sem travar a tela.
        viewModelScope.launch {
            try {
                // Pede os 151 primeiros Pokémon (só nome e url de cada um).
                val response = PokeApiClient.api.getlistPokemons(limit = 151)
                // Converte cada item da API (DTO) no modelo do app (Pokemon).
                val pokemons = response.results.map { dto ->
                    // A url é algo como .../pokemon/1/ : tira a '/' final, pega o texto depois da última '/' e vira número.
                    val id = dto.url.trimEnd('/').substringAfterLast('/').toInt()
                    Pokemon(id = id, name = dto.name)
                }
                _state.value = ListUiState.Success(pokemons )
            // Sem internet ou erro na resposta: em vez de fechar o app, vira um estado de erro que a tela sabe mostrar.
            } catch (e: Exception) {
                _state.value = ListUiState.Error("Não foi possível carregar")
            }
        }
    }
}