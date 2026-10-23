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

@Composable
fun ListScreen(
    pokemons: List<Pokemon>,
    onPokemonClick: (Pokemon) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(pokemons) { pokemon ->

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onPokemonClick(pokemon) }
            ) {
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

