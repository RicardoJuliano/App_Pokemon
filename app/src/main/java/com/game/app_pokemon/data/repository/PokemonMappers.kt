package com.game.app_pokemon.data.repository

import com.game.app_pokemon.data.local.PokemonEntity
import com.game.app_pokemon.data.remote.PokemonDetailDto
import com.game.app_pokemon.domain.Pokemon

// Conversores entre as camadas. Cada camada tem seu próprio modelo:
// DTO (formato da API) -> Entity (formato do banco Room) -> Pokemon (formato que a tela usa).
// São "funções de extensão": dentro delas, `id`, `name` etc. são os campos do objeto antes do ponto.

// DTO -> Entity: transforma a resposta da API na linha que será salva no banco.
fun PokemonDetailDto.toEntity(): PokemonEntity {

    // A API devolve os atributos numa lista ("hp", "attack"...); esta função acha o valor pelo nome.
    // first { } lança NoSuchElementException se o atributo não existir na lista.
    fun stat(name: String) = stats.first { it.stat.name == name }.baseStat

    return PokemonEntity(
        id = id,
        name = name,
        hp = stat("hp"),
        attack = stat("attack"),
        defense = stat("defense"),
        specialAttack = stat("special-attack"),
        specialDefense = stat("special-defense"),
        speed = stat("speed"),
        // O Room guarda texto simples, então a lista de tipos vira "grass,poison".
        types = types.joinToString(",") { it.type.name }
    )
}


// Entity -> Pokemon: leva os 6 atributos de batalha; só os tipos ficam de fora (a tela e o jogo não usam).
fun PokemonEntity.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
        speed = speed
    )
}
