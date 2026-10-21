package com.game.app_pokemon.ui.list

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

        is ListUiState.Loading -> Text("Carregando")
        is ListUiState.Error -> Text(current.message)
        is ListUiState.Success -> ListScreen(
            pokemons = current.pokemons,
            onPokemonClick = onPokemonClick
        )
    }


}