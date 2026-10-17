package com.game.app_pokemon.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.game.app_pokemon.domain.Pokemon
import com.game.app_pokemon.ui.theme.App_PokemonTheme

@Composable
fun ListScreen( pokemons:List<Pokemon>,
                onPokemonClick: (Pokemon) -> Unit){
    LazyColumn {
        items(pokemons) { pokemon ->
            Text(text = pokemon.name,
                modifier = Modifier.clickable{onPokemonClick(pokemon)})
        }
    }
}

@Preview (showBackground = true)
@Composable
fun ListScreenPreview() {
    App_PokemonTheme {
        Surface {
            var escolhido by remember { mutableStateOf("nenhum") }

            Column {
                Text("Pokemon escolhido: $escolhido")

                ListScreen(
                    pokemons = listOf(
                        Pokemon(1, "Bulbasaur"),
                        Pokemon(2, "Ivysaur"),
                        Pokemon(3, "Venusaur"),
                        Pokemon(4, "Charmander"),
                        Pokemon(5, "Charmeleon")
                    ),
                    onPokemonClick = { pokemon -> escolhido = pokemon.name }
                )


            }


        }
    }
}