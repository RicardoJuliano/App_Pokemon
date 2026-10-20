package com.game.app_pokemon.domain

// Modelo do app (domínio): é o que as telas conhecem, independente do formato da API.
data class Pokemon(val id: Int, val name: String ){
    // Propriedade calculada (não é guardada): monta o endereço do sprite a partir do id,
    // evitando uma chamada extra à API só para descobrir a imagem.
    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
}
