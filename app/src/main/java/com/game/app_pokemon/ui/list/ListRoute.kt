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
fun ListRoute(
    onPokemonClick: (Pokemon) -> Unit,
    viewModel: ListViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val state by viewModel.state.collectAsState()

    when (val current = state) {

        is ListUiState.Loading ->Column  {Text("Carregando") }
        is ListUiState.Error -> Column {
            Text(current.message)
            Button(onClick = {viewModel.retry()}) {Text("Tentar Novamente") }
        }
        is ListUiState.Success -> ListScreen(
            pokemons = current.pokemons,
            onPokemonClick = onPokemonClick
        )
    }


}