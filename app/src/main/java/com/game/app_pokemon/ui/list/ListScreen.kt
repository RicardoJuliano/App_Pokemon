package com.game.app_pokemon.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.game.app_pokemon.domain.Pokemon
import com.game.app_pokemon.ui.theme.App_PokemonTheme

@Composable
// Tela burra (stateless): só desenha a lista recebida e avisa o clique por callback.
// Por isso funciona no Preview com dados falsos, sem internet e sem ViewModel.
fun ListScreen( pokemons:List<Pokemon>,
                onPokemonClick: (Pokemon) -> Unit){
    // Lista rolável que só desenha os itens visíveis (importante para 151 itens com imagem).
    LazyColumn {
        items(pokemons) { pokemon ->

                // Uma linha da lista (imagem + nome). O clique fica no Row para a linha toda responder ao toque.
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onPokemonClick(pokemon) }
                ) {
                AsyncImage(
                    // Coil baixa a imagem em segundo plano e guarda em cache.
                    model = pokemon.imageUrl,
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(64.dp)


                )
                Text(text = pokemon.name)
            }


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