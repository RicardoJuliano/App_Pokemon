package com.game.app_pokemon.domain

// Modelo do app (domínio): é o que as telas e o jogo conhecem, independente do formato da API.
// Carrega os 6 atributos para montar o Combatant da batalha sem ir ao banco de novo.
data class Pokemon(
    val id: Int,
    val name: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
) {
    // Propriedade calculada (não é guardada): monta o endereço do sprite a partir do id,
    // evitando uma chamada extra à API só para descobrir a imagem.
    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
}
