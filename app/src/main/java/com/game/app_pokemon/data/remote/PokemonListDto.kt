package com.game.app_pokemon.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListDto(
    val results: List<PokemonListItenDto>,
)

@Serializable
data class PokemonListItenDto(
    val name: String,
    val url: String
)