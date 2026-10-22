package com.game.app_pokemon.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.game.app_pokemon.domain.Pokemon
import com.game.app_pokemon.ui.AppViewModelProvider

@Composable
// Camada que liga o ViewModel à tela: lê o estado e decide o que mostrar (carregando, erro ou lista).
fun ListRoute(
    onPokemonClick: (Pokemon) -> Unit,
    // factory: o ListViewModel exige um Repository no construtor, então o Android precisa da receita para montá-lo.
    viewModel: ListViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    // Transforma o StateFlow em estado do Compose: quando o ViewModel muda o estado, a tela recompõe sozinha.
    val state by viewModel.state.collectAsState()

    // Cobre os 3 estados possíveis; como o estado é 'sealed', o compilador exige todos.
    when (val current = state) {

        is ListUiState.Loading ->Column  {Text("Carregando") }
        // Erro: mostra a mensagem e um botão. O clique só avisa o ViewModel (a tela não decide como recarregar).
        is ListUiState.Error -> Column {
            Text(current.message)
            Button(onClick = {viewModel.retry()}) {Text("Tentar Novamente") }
        }
        // Só aqui a tela de lista entra em cena: ela recebe a lista pronta e nem sabe que existem carregando/erro.
        is ListUiState.Success -> ListScreen(
            pokemons = current.pokemons,
            onPokemonClick = onPokemonClick
        )
    }


}