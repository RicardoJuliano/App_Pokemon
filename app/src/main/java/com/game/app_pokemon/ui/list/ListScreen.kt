package com.game.app_pokemon.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.game.app_pokemon.domain.Pokemon

// Tela burra (stateless): só desenha a lista recebida e avisa o clique por callback.
@Composable
fun ListScreen(
    pokemons: List<Pokemon>,
    onPokemonClick: (Pokemon) -> Unit
) {
    // Lista rolável que só desenha os itens visíveis (importante para 151 itens com imagem).
    // fillMaxWidth: a lista ocupa a largura toda, então dá para rolar com o dedo em qualquer ponto da tela.
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(pokemons) { pokemon ->

            // Uma linha da lista (imagem + nome). O clique fica no Row; a área tocável é a do conteúdo.
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onPokemonClick(pokemon) }
            ) {
                // Coil baixa a imagem em segundo plano e guarda em cache.
                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(64.dp)


                )
                Text(text = pokemon.name.uppercase())
            }


        }
    }
}

