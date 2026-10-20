package com.game.app_pokemon.domain

data class Pokemon(val id: Int, val name: String ){
    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
}
