package com.game.app_pokemon.data.remote

import kotlinx.serialization.Serializable

@Serializable
// Resposta da lista: o JSON traz um objeto com a chave 'results', que é um array ([]) de itens.
data class PokemonListDto(
    val results: List<PokemonListItenDto>,
)

@Serializable
// Cada item da lista só traz o nome e a URL do detalhe. O id do Pokémon está no fim dessa URL.
data class PokemonListItenDto(
    val name: String,
    val url: String
)