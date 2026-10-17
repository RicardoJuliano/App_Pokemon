package com.game.app_pokemon.ui.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.game.app_pokemon.domain.Pokemon

@Composable
fun ListScreen( pokemons:List<Pokemon>){
    LazyColumn {
        items(pokemons) { pokemon ->
            Text(text = pokemon.name)
        }
    }
}

@Preview
@Composable
fun ListScreenPreview(){
    ListScreen(
        pokemons = listOf(
            Pokemon(1,"Bulbasaur"),
            Pokemon(2,"Ivysaur"),
            Pokemon(3,"Venusaur"),
            Pokemon(4,"Charmander"),
            Pokemon(5,"Charmeleon"),

        )
    )
}